package com.qrmenu.platform.ordering.service;

import com.qrmenu.platform.common.exception.ResourceNotFoundException;
import com.qrmenu.platform.notification.NotificationService;
import com.qrmenu.platform.ordering.dto.CreateOrderItemRequest;
import com.qrmenu.platform.ordering.dto.CreateOrderRequest;
import com.qrmenu.platform.ordering.dto.OrderItemResponse;
import com.qrmenu.platform.ordering.dto.OrderResponse;
import com.qrmenu.platform.ordering.dto.SalesReportResponse;
import com.qrmenu.platform.ordering.entity.CustomerOrder;
import com.qrmenu.platform.ordering.entity.OrderItem;
import com.qrmenu.platform.ordering.entity.OrderStatus;
import com.qrmenu.platform.ordering.repository.CustomerOrderRepository;
import com.qrmenu.platform.table.entity.RestaurantTable;
import com.qrmenu.platform.table.repository.RestaurantTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final CustomerOrderRepository orderRepository;
    private final RestaurantTableRepository tableRepository;
    private final NotificationService notificationService;

    public OrderService(CustomerOrderRepository orderRepository, RestaurantTableRepository tableRepository, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public OrderResponse createOrderFromQrToken(String token, CreateOrderRequest request) {
        RestaurantTable table = tableRepository.findByQrToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Table", token));

        var branch = table.getBranch();

        String paymentMethod = (request.getPaymentMethod() != null && !request.getPaymentMethod().isBlank())
                ? request.getPaymentMethod().trim().toUpperCase()
                : "CARD";

        BigDecimal tipAmount = (request.getTipAmount() != null && request.getTipAmount().compareTo(BigDecimal.ZERO) >= 0)
                ? request.getTipAmount().setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        // Calculate total amount & construct order
        BigDecimal calculatedTotal = BigDecimal.ZERO;

        CustomerOrder order = CustomerOrder.builder()
                .branch(branch)
                .table(table)
                .orderNumber("ORD-" + (1000 + RANDOM.nextInt(9000)))
                .status(OrderStatus.PENDING)
                .paymentMethod(paymentMethod)
                .tipAmount(tipAmount)
                .notes(request.getNotes())
                .build();

        for (CreateOrderItemRequest itemReq : request.getItems()) {
            BigDecimal lineTotal = itemReq.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            calculatedTotal = calculatedTotal.add(lineTotal);

            OrderItem item = OrderItem.builder()
                    .productName(itemReq.getProductName())
                    .quantity(itemReq.getQuantity())
                    .unitPrice(itemReq.getUnitPrice())
                    .totalPrice(lineTotal)
                    .selectedVariantName(itemReq.getSelectedVariantName())
                    .selectedOptionsText(itemReq.getSelectedOptionsText())
                    .notes(itemReq.getNotes())
                    .build();

            order.addItem(item);
        }

        calculatedTotal = calculatedTotal.add(tipAmount).setScale(2, RoundingMode.HALF_UP);
        order.setTotalAmount(calculatedTotal);

        CustomerOrder saved = orderRepository.save(order);
        OrderResponse response = mapToResponse(saved);

        // Broadcast new order to kitchen display
        notificationService.notifyKitchen(branch.getId(), response);

        return response;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getActiveOrdersForBranch(Long branchId) {
        List<OrderStatus> activeStatuses = List.of(OrderStatus.PENDING, OrderStatus.PREPARING, OrderStatus.READY);
        List<CustomerOrder> orders = orderRepository.findByBranchIdAndStatusInOrderByCreatedAtAsc(branchId, activeStatuses);
        if (orders.isEmpty()) {
            orders = orderRepository.findAll().stream()
                    .filter(o -> activeStatuses.contains(o.getStatus()))
                    .collect(Collectors.toList());
        }
        return orders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrdersForBranch(Long branchId) {
        List<CustomerOrder> orders = orderRepository.findByBranchIdOrderByCreatedAtDesc(branchId);
        if (orders.isEmpty()) {
            orders = orderRepository.findAll().stream()
                    .sorted((a, b) -> {
                        if (a.getCreatedAt() == null || b.getCreatedAt() == null) return 0;
                        return b.getCreatedAt().compareTo(a.getCreatedAt());
                    })
                    .collect(Collectors.toList());
        }
        return orders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SalesReportResponse getSalesReportForBranch(Long branchId) {
        List<OrderResponse> allOrders = getAllOrdersForBranch(branchId);
        List<OrderResponse> validOrders = allOrders.stream()
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .toList();

        BigDecimal grossRevenue = validOrders.stream()
                .map(o -> o.getTotalAmount() != null ? o.getTotalAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int orderCount = validOrders.size();
        BigDecimal avgOrderValue = orderCount > 0
                ? grossRevenue.divide(BigDecimal.valueOf(orderCount), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        int activeCount = (int) validOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.PENDING
                        || o.getStatus() == OrderStatus.PREPARING
                        || o.getStatus() == OrderStatus.READY)
                .count();

        return SalesReportResponse.builder()
                .branchId(branchId)
                .grossRevenue(grossRevenue)
                .orderCount(orderCount)
                .avgOrderValue(avgOrderValue)
                .activeOrderCount(activeCount)
                .orders(allOrders)
                .build();
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        order.setStatus(newStatus);
        CustomerOrder updated = orderRepository.save(order);
        OrderResponse response = mapToResponse(updated);

        // Broadcast status update to kitchen and customer
        notificationService.notifyKitchen(order.getBranch().getId(), response);
        notificationService.notifyCustomerOrderStatus(order.getTable().getQrToken(), response);

        return response;
    }

    private OrderResponse mapToResponse(CustomerOrder order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.getTotalPrice())
                        .selectedVariantName(item.getSelectedVariantName())
                        .selectedOptionsText(item.getSelectedOptionsText())
                        .notes(item.getNotes())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .tableId(order.getTable().getId())
                .tableName("Table " + order.getTable().getTableNumber())
                .branchId(order.getBranch().getId())
                .branchName(order.getBranch().getName())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod() : "CARD")
                .tipAmount(order.getTipAmount() != null ? order.getTipAmount() : BigDecimal.ZERO)
                .notes(order.getNotes())
                .items(itemResponses)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
