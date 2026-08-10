package com.qrmenu.platform.servicerequest.dto;

import com.qrmenu.platform.servicerequest.entity.ServiceRequestStatus;
import com.qrmenu.platform.servicerequest.entity.ServiceRequestType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceRequestResponse {
    private Long id;
    private String tableName;
    private String branchName;
    private ServiceRequestType type;
    private ServiceRequestStatus status;
    private String message;
    private String resolvedByName;
    private String createdAt;
    private String resolvedAt;
}
