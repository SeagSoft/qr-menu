package com.qrmenu.platform.ordering.controller;

import com.qrmenu.platform.ordering.dto.OrderResponse;
import com.qrmenu.platform.ordering.dto.SalesReportResponse;
import com.qrmenu.platform.ordering.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<OrderResponse>> getBranchOrders(@PathVariable Long branchId) {
        return ResponseEntity.ok(orderService.getAllOrdersForBranch(branchId));
    }

    @GetMapping("/branch/{branchId}/report")
    public ResponseEntity<SalesReportResponse> getBranchSalesReport(@PathVariable Long branchId) {
        return ResponseEntity.ok(orderService.getSalesReportForBranch(branchId));
    }
}
