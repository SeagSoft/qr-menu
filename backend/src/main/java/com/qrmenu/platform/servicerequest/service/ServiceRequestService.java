package com.qrmenu.platform.servicerequest.service;

import com.qrmenu.platform.common.exception.ResourceNotFoundException;
import com.qrmenu.platform.notification.NotificationService;
import com.qrmenu.platform.servicerequest.dto.CreateServiceRequest;
import com.qrmenu.platform.servicerequest.dto.ServiceRequestResponse;
import com.qrmenu.platform.servicerequest.entity.ServiceRequest;
import com.qrmenu.platform.servicerequest.entity.ServiceRequestStatus;
import com.qrmenu.platform.servicerequest.repository.ServiceRequestRepository;
import com.qrmenu.platform.table.entity.RestaurantTable;
import com.qrmenu.platform.table.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final RestaurantTableRepository tableRepository;
    private final NotificationService notificationService;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Customer creates a service request via QR token.
     */
    @Transactional
    public ServiceRequestResponse createFromToken(String qrToken, CreateServiceRequest request) {
        RestaurantTable table = tableRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new ResourceNotFoundException("Table", qrToken));

        ServiceRequest serviceRequest = ServiceRequest.builder()
                .branch(table.getBranch())
                .table(table)
                .type(request.getType())
                .status(ServiceRequestStatus.PENDING)
                .message(request.getMessage())
                .build();

        serviceRequest = serviceRequestRepository.save(serviceRequest);
        ServiceRequestResponse response = toResponse(serviceRequest);

        // Broadcast to staff screens
        notificationService.notifyServiceRequest(table.getBranch().getId(), response);

        return response;
    }

    /**
     * Staff retrieves active (pending/acknowledged) service requests for their branch.
     */
    @Transactional(readOnly = true)
    public List<ServiceRequestResponse> getActiveByBranch(Long branchId) {
        List<ServiceRequestStatus> activeStatuses = List.of(
                ServiceRequestStatus.PENDING, ServiceRequestStatus.ACKNOWLEDGED);
        return serviceRequestRepository
                .findByBranchIdAndStatusInOrderByCreatedAtDesc(branchId, activeStatuses)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Staff retrieves all service requests for their branch.
     */
    @Transactional(readOnly = true)
    public List<ServiceRequestResponse> getAllByBranch(Long branchId) {
        return serviceRequestRepository.findByBranchIdOrderByCreatedAtDesc(branchId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Staff acknowledges or resolves a service request.
     */
    @Transactional
    public ServiceRequestResponse updateStatus(Long branchId, Long requestId, ServiceRequestStatus newStatus) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceRequest", requestId));

        if (!serviceRequest.getBranch().getId().equals(branchId)) {
            throw new ResourceNotFoundException("ServiceRequest", requestId);
        }

        serviceRequest.setStatus(newStatus);
        if (newStatus == ServiceRequestStatus.RESOLVED || newStatus == ServiceRequestStatus.CANCELLED) {
            serviceRequest.setResolvedAt(OffsetDateTime.now());
        }

        serviceRequest = serviceRequestRepository.save(serviceRequest);
        return toResponse(serviceRequest);
    }

    private ServiceRequestResponse toResponse(ServiceRequest sr) {
        return ServiceRequestResponse.builder()
                .id(sr.getId())
                .tableName(sr.getTable().getTableNumber())
                .branchName(sr.getBranch().getName())
                .type(sr.getType())
                .status(sr.getStatus())
                .message(sr.getMessage())
                .resolvedByName(sr.getResolvedBy() != null ? sr.getResolvedBy().getFullName() : null)
                .createdAt(sr.getCreatedAt() != null ? sr.getCreatedAt().format(TIME_FORMATTER) : null)
                .resolvedAt(sr.getResolvedAt() != null ? sr.getResolvedAt().format(TIME_FORMATTER) : null)
                .build();
    }
}
