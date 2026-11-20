package com.qrmenu.platform.ordering.controller;

import com.qrmenu.platform.ordering.dto.OrderResponse;
import com.qrmenu.platform.ordering.entity.OrderStatus;
import com.qrmenu.platform.ordering.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff/branches/{branchId}/orders")
public class StaffOrderController {

    private final OrderService orderService;

    public StaffOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getActiveOrders(@PathVariable Long branchId) {
        List<OrderResponse> orders = orderService.getActiveOrdersForBranch(branchId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderResponse>> getOrderHistory(@PathVariable Long branchId) {
        List<OrderResponse> orders = orderService.getAllOrdersForBranch(branchId);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long branchId,
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {
        OrderResponse response = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(response);
    }
}
