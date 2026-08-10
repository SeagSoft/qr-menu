package com.qrmenu.platform.ordering;

import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.ordering.dto.CreateOrderItemRequest;
import com.qrmenu.platform.ordering.dto.CreateOrderRequest;
import com.qrmenu.platform.ordering.dto.OrderResponse;
import com.qrmenu.platform.ordering.entity.CustomerOrder;
import com.qrmenu.platform.ordering.entity.OrderStatus;
import com.qrmenu.platform.ordering.repository.CustomerOrderRepository;
import com.qrmenu.platform.ordering.service.OrderService;
import com.qrmenu.platform.table.entity.RestaurantTable;
import com.qrmenu.platform.table.repository.RestaurantTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CustomerOrderRepository orderRepository;

    @Mock
    private RestaurantTableRepository tableRepository;

    @Mock
    private com.qrmenu.platform.notification.NotificationService notificationService;

    @InjectMocks
    private OrderService orderService;

    private RestaurantTable testTable;
    private Branch testBranch;

    @BeforeEach
    void setUp() {
        testBranch = Branch.builder().id(10L).name("Test Branch").build();
        testTable = RestaurantTable.builder()
                .id(100L)
                .tableNumber("01")
                .qrToken("tbl-001")
                .branch(testBranch)
                .build();
    }

    @Test
    void createOrderFromQrToken_success() {
        when(tableRepository.findByQrToken("tbl-001")).thenReturn(Optional.of(testTable));
        when(orderRepository.save(any(CustomerOrder.class))).thenAnswer(invocation -> {
            CustomerOrder order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        CreateOrderRequest request = new CreateOrderRequest();
        CreateOrderItemRequest item = new CreateOrderItemRequest();
        item.setProductName("Wagyu Steak");
        item.setQuantity(2);
        item.setUnitPrice(new BigDecimal("400.00"));
        request.setItems(List.of(item));

        OrderResponse response = orderService.createOrderFromQrToken("tbl-001", request);

        assertNotNull(response);
        assertEquals(new BigDecimal("800.00"), response.getTotalAmount());
        assertEquals(OrderStatus.PENDING, response.getStatus());
        assertEquals("Table 01", response.getTableName());
        verify(orderRepository, times(1)).save(any());
    }
}
