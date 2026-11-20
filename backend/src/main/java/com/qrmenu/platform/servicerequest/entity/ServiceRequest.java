package com.qrmenu.platform.servicerequest.entity;

import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.table.entity.RestaurantTable;
import com.qrmenu.platform.auth.entity.StaffUser;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "service_requests")
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTable table;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 32)
    private ServiceRequestType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private ServiceRequestStatus status;
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by")
    private StaffUser resolvedBy;
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @Column(name = "resolved_at")
    private OffsetDateTime resolvedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
        if (this.status == null) {
            this.status = ServiceRequestStatus.PENDING;
        }
    }


    public static class ServiceRequestBuilder {
        private Long id;
        private Branch branch;
        private RestaurantTable table;
        private ServiceRequestType type;
        private ServiceRequestStatus status;
        private String message;
        private StaffUser resolvedBy;
        private OffsetDateTime createdAt;
        private OffsetDateTime resolvedAt;

        ServiceRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequest.ServiceRequestBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequest.ServiceRequestBuilder branch(Branch branch) {
            this.branch = branch;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequest.ServiceRequestBuilder table(RestaurantTable table) {
            this.table = table;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequest.ServiceRequestBuilder type(ServiceRequestType type) {
            this.type = type;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequest.ServiceRequestBuilder status(ServiceRequestStatus status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequest.ServiceRequestBuilder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequest.ServiceRequestBuilder resolvedBy(StaffUser resolvedBy) {
            this.resolvedBy = resolvedBy;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequest.ServiceRequestBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ServiceRequest.ServiceRequestBuilder resolvedAt(OffsetDateTime resolvedAt) {
            this.resolvedAt = resolvedAt;
            return this;
        }

        public ServiceRequest build() {
            return new ServiceRequest(this.id, this.branch, this.table, this.type, this.status, this.message, this.resolvedBy, this.createdAt, this.resolvedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ServiceRequest.ServiceRequestBuilder(id=" + this.id + ", branch=" + this.branch + ", table=" + this.table + ", type=" + this.type + ", status=" + this.status + ", message=" + this.message + ", resolvedBy=" + this.resolvedBy + ", createdAt=" + this.createdAt + ", resolvedAt=" + this.resolvedAt + ")";
        }
    }

    public static ServiceRequest.ServiceRequestBuilder builder() {
        return new ServiceRequest.ServiceRequestBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public RestaurantTable getTable() {
        return this.table;
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

    public StaffUser getResolvedBy() {
        return this.resolvedBy;
    }

    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    public OffsetDateTime getResolvedAt() {
        return this.resolvedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
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

    public void setResolvedBy(StaffUser resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setResolvedAt(OffsetDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public ServiceRequest() {
    }

    public ServiceRequest(Long id, Branch branch, RestaurantTable table, ServiceRequestType type, ServiceRequestStatus status, String message, StaffUser resolvedBy, OffsetDateTime createdAt, OffsetDateTime resolvedAt) {
        this.id = id;
        this.branch = branch;
        this.table = table;
        this.type = type;
        this.status = status;
        this.message = message;
        this.resolvedBy = resolvedBy;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }
}
