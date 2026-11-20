package com.qrmenu.platform.servicerequest.controller;

import com.qrmenu.platform.servicerequest.dto.CreateServiceRequest;
import com.qrmenu.platform.servicerequest.dto.ServiceRequestResponse;
import com.qrmenu.platform.servicerequest.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Public endpoint for customers to create service requests via their QR token.
 */
@RestController
@RequestMapping("/api/public/tables/{token}/service-requests")
public class PublicServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public PublicServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @PostMapping
    public ResponseEntity<ServiceRequestResponse> createServiceRequest(
            @PathVariable String token,
            @Valid @RequestBody CreateServiceRequest request) {
        ServiceRequestResponse response = serviceRequestService.createFromToken(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
