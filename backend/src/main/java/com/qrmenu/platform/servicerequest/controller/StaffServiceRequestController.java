package com.qrmenu.platform.servicerequest.controller;

import com.qrmenu.platform.servicerequest.dto.ServiceRequestResponse;
import com.qrmenu.platform.servicerequest.entity.ServiceRequestStatus;
import com.qrmenu.platform.servicerequest.service.ServiceRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Staff-facing controller for managing service requests.
 */
@RestController
@RequestMapping("/api/staff/branches/{branchId}/service-requests")
public class StaffServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public StaffServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequestResponse>> getActiveRequests(
            @PathVariable Long branchId) {
        return ResponseEntity.ok(serviceRequestService.getActiveByBranch(branchId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceRequestResponse>> getAllRequests(
            @PathVariable Long branchId) {
        return ResponseEntity.ok(serviceRequestService.getAllByBranch(branchId));
    }

    @PatchMapping("/{requestId}/status")
    public ResponseEntity<ServiceRequestResponse> updateStatus(
            @PathVariable Long branchId,
            @PathVariable Long requestId,
            @RequestParam ServiceRequestStatus status) {
        return ResponseEntity.ok(
                serviceRequestService.updateStatus(branchId, requestId, status));
    }
}
