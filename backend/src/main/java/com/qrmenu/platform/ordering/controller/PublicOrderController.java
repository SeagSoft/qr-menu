package com.qrmenu.platform.ordering.controller;

import com.qrmenu.platform.ordering.dto.CreateOrderRequest;
import com.qrmenu.platform.ordering.dto.OrderResponse;
import com.qrmenu.platform.ordering.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/tables/{token}/orders")
public class PublicOrderController {

    private final OrderService orderService;

    public PublicOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> submitOrder(
            @PathVariable String token,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        OrderResponse response = orderService.createOrderFromQrToken(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
