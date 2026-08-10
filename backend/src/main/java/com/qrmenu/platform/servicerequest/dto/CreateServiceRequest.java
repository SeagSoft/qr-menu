package com.qrmenu.platform.servicerequest.dto;

import com.qrmenu.platform.servicerequest.entity.ServiceRequestType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateServiceRequest {

    @NotNull(message = "Service request type is required")
    private ServiceRequestType type;

    private String message;
}
