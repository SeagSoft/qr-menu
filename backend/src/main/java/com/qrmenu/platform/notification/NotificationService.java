package com.qrmenu.platform.notification;

import com.qrmenu.platform.ordering.dto.OrderResponse;
import com.qrmenu.platform.servicerequest.dto.ServiceRequestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Centralized notification service for broadcasting real-time events
 * to WebSocket subscribers (Kitchen, Staff, Customer screens).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Notify kitchen display of a new or updated order.
     */
    public void notifyKitchen(Long branchId, OrderResponse order) {
        String destination = "/topic/kitchen/" + branchId;
        log.info("Broadcasting order {} to kitchen (branch={})", order.getOrderNumber(), branchId);
        messagingTemplate.convertAndSend(destination, order);
    }

    /**
     * Notify staff of a new service request (waiter call, bill request).
     */
    public void notifyServiceRequest(Long branchId, ServiceRequestResponse request) {
        String destination = "/topic/service-requests/" + branchId;
        log.info("Broadcasting service request {} to staff (branch={})", request.getId(), branchId);
        messagingTemplate.convertAndSend(destination, request);
    }

    /**
     * Notify customer table screen of order status change.
     */
    public void notifyCustomerOrderStatus(String tableToken, OrderResponse order) {
        String destination = "/topic/orders/" + tableToken;
        log.info("Broadcasting order status {} to customer table {}", order.getStatus(), tableToken);
        messagingTemplate.convertAndSend(destination, order);
    }
}
