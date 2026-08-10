package com.qrmenu.platform.servicerequest.repository;

import com.qrmenu.platform.servicerequest.entity.ServiceRequest;
import com.qrmenu.platform.servicerequest.entity.ServiceRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findByBranchIdAndStatusInOrderByCreatedAtDesc(
            Long branchId, List<ServiceRequestStatus> statuses);

    List<ServiceRequest> findByBranchIdOrderByCreatedAtDesc(Long branchId);

    List<ServiceRequest> findByTableIdAndStatusOrderByCreatedAtDesc(
            Long tableId, ServiceRequestStatus status);
}
