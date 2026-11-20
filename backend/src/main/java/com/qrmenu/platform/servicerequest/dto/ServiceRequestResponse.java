package com.qrmenu.platform.servicerequest.dto;

import com.qrmenu.platform.servicerequest.entity.ServiceRequestStatus;
import com.qrmenu.platform.servicerequest.entity.ServiceRequestType;

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

    ServiceRequestResponse(Long id, String tableName, String branchName, ServiceRequestType type, ServiceRequestStatus status, String message, String resolvedByName, String createdAt, String resolvedAt) {
        this.id = id;
        this.tableName = tableName;
        this.branchName = branchName;
        this.type = type;
        this.status = status;
        this.message = message;
        this.resolvedByName = resolvedByName;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }


    public static class ServiceRequestResponseBuilder {
        private Long id;
        private String tableName;
        private String branchName;
        private ServiceRequestType type;
        private ServiceRequestStatus status;
        private String message;
        private String resolvedByName;
        private String createdAt;
        private String resolvedAt;

        ServiceRequestResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequestResponse.ServiceRequestResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequestResponse.ServiceRequestResponseBuilder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequestResponse.ServiceRequestResponseBuilder branchName(String branchName) {
            this.branchName = branchName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequestResponse.ServiceRequestResponseBuilder type(ServiceRequestType type) {
            this.type = type;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequestResponse.ServiceRequestResponseBuilder status(ServiceRequestStatus status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequestResponse.ServiceRequestResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequestResponse.ServiceRequestResponseBuilder resolvedByName(String resolvedByName) {
            this.resolvedByName = resolvedByName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequestResponse.ServiceRequestResponseBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequestResponse.ServiceRequestResponseBuilder resolvedAt(String resolvedAt) {
            this.resolvedAt = resolvedAt;
            return this;
        }

        public ServiceRequestResponse build() {
            return new ServiceRequestResponse(this.id, this.tableName, this.branchName, this.type, this.status, this.message, this.resolvedByName, this.createdAt, this.resolvedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ServiceRequestResponse.ServiceRequestResponseBuilder(id=" + this.id + ", tableName=" + this.tableName + ", branchName=" + this.branchName + ", type=" + this.type + ", status=" + this.status + ", message=" + this.message + ", resolvedByName=" + this.resolvedByName + ", createdAt=" + this.createdAt + ", resolvedAt=" + this.resolvedAt + ")";
        }
    }

    public static ServiceRequestResponse.ServiceRequestResponseBuilder builder() {
        return new ServiceRequestResponse.ServiceRequestResponseBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public ServiceRequestType getType() {
        return this.type;
    }

    public ServiceRequestStatus getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public String getResolvedByName() {
        return this.resolvedByName;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public String getResolvedAt() {
        return this.resolvedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setType(ServiceRequestType type) {
        this.type = type;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResolvedByName(String resolvedByName) {
        this.resolvedByName = resolvedByName;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setResolvedAt(String resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ServiceRequestResponse)) return false;
        ServiceRequestResponse other = (ServiceRequestResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$id = this.getId();
        java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        java.lang.Object this$tableName = this.getTableName();
        java.lang.Object other$tableName = other.getTableName();
        if (this$tableName == null ? other$tableName != null : !this$tableName.equals(other$tableName)) return false;
        java.lang.Object this$branchName = this.getBranchName();
        java.lang.Object other$branchName = other.getBranchName();
        if (this$branchName == null ? other$branchName != null : !this$branchName.equals(other$branchName)) return false;
        java.lang.Object this$type = this.getType();
        java.lang.Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        java.lang.Object this$status = this.getStatus();
        java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        java.lang.Object this$message = this.getMessage();
        java.lang.Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        java.lang.Object this$resolvedByName = this.getResolvedByName();
        java.lang.Object other$resolvedByName = other.getResolvedByName();
        if (this$resolvedByName == null ? other$resolvedByName != null : !this$resolvedByName.equals(other$resolvedByName)) return false;
        java.lang.Object this$createdAt = this.getCreatedAt();
        java.lang.Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        java.lang.Object this$resolvedAt = this.getResolvedAt();
        java.lang.Object other$resolvedAt = other.getResolvedAt();
        if (this$resolvedAt == null ? other$resolvedAt != null : !this$resolvedAt.equals(other$resolvedAt)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof ServiceRequestResponse;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        java.lang.Object $tableName = this.getTableName();
        result = result * PRIME + ($tableName == null ? 43 : $tableName.hashCode());
        java.lang.Object $branchName = this.getBranchName();
        result = result * PRIME + ($branchName == null ? 43 : $branchName.hashCode());
        java.lang.Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        java.lang.Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        java.lang.Object $resolvedByName = this.getResolvedByName();
        result = result * PRIME + ($resolvedByName == null ? 43 : $resolvedByName.hashCode());
        java.lang.Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        java.lang.Object $resolvedAt = this.getResolvedAt();
        result = result * PRIME + ($resolvedAt == null ? 43 : $resolvedAt.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ServiceRequestResponse(id=" + this.getId() + ", tableName=" + this.getTableName() + ", branchName=" + this.getBranchName() + ", type=" + this.getType() + ", status=" + this.getStatus() + ", message=" + this.getMessage() + ", resolvedByName=" + this.getResolvedByName() + ", createdAt=" + this.getCreatedAt() + ", resolvedAt=" + this.getResolvedAt() + ")";
    }
}
