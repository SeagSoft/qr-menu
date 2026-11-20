package com.qrmenu.platform.servicerequest.dto;

import com.qrmenu.platform.servicerequest.entity.ServiceRequestType;
import jakarta.validation.constraints.NotNull;

public class CreateServiceRequest {
    @NotNull(message = "Service request type is required")
    private ServiceRequestType type;
    private String message;

    public CreateServiceRequest() {
    }

    public ServiceRequestType getType() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setType(ServiceRequestType type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateServiceRequest)) return false;
        CreateServiceRequest other = (CreateServiceRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$type = this.getType();
        java.lang.Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        java.lang.Object this$message = this.getMessage();
        java.lang.Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof CreateServiceRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        java.lang.Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "CreateServiceRequest(type=" + this.getType() + ", message=" + this.getMessage() + ")";
    }
}
