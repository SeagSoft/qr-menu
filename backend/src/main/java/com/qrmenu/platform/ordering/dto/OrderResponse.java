package com.qrmenu.platform.ordering.dto;

import com.qrmenu.platform.ordering.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private String orderNumber;
    private Long tableId;
    private String tableName;
    private Long branchId;
    private String branchName;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private BigDecimal tipAmount;
    private String notes;
    private List<OrderItemResponse> items;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    OrderResponse(Long id, String orderNumber, Long tableId, String tableName, Long branchId, String branchName, OrderStatus status, BigDecimal totalAmount, String paymentMethod, BigDecimal tipAmount, String notes, List<OrderItemResponse> items, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.tableId = tableId;
        this.tableName = tableName;
        this.branchId = branchId;
        this.branchName = branchName;
        this.status = status;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.tipAmount = tipAmount;
        this.notes = notes;
        this.items = items;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static class OrderResponseBuilder {
        private Long id;
        private String orderNumber;
        private Long tableId;
        private String tableName;
        private Long branchId;
        private String branchName;
        private OrderStatus status;
        private BigDecimal totalAmount;
        private String paymentMethod;
        private BigDecimal tipAmount;
        private String notes;
        private List<OrderItemResponse> items;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        OrderResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder tableId(Long tableId) {
            this.tableId = tableId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder branchId(Long branchId) {
            this.branchId = branchId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder branchName(String branchName) {
            this.branchName = branchName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder tipAmount(BigDecimal tipAmount) {
            this.tipAmount = tipAmount;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder items(List<OrderItemResponse> items) {
            this.items = items;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderResponse.OrderResponseBuilder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public OrderResponse build() {
            return new OrderResponse(this.id, this.orderNumber, this.tableId, this.tableName, this.branchId, this.branchName, this.status, this.totalAmount, this.paymentMethod, this.tipAmount, this.notes, this.items, this.createdAt, this.updatedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "OrderResponse.OrderResponseBuilder(id=" + this.id + ", orderNumber=" + this.orderNumber + ", tableId=" + this.tableId + ", tableName=" + this.tableName + ", branchId=" + this.branchId + ", branchName=" + this.branchName + ", status=" + this.status + ", totalAmount=" + this.totalAmount + ", paymentMethod=" + this.paymentMethod + ", tipAmount=" + this.tipAmount + ", notes=" + this.notes + ", items=" + this.items + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }

    public static OrderResponse.OrderResponseBuilder builder() {
        return new OrderResponse.OrderResponseBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public Long getTableId() {
        return this.tableId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public BigDecimal getTipAmount() {
        return this.tipAmount;
    }

    public String getNotes() {
        return this.notes;
    }

    public List<OrderItemResponse> getItems() {
        return this.items;
    }

    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setTipAmount(BigDecimal tipAmount) {
        this.tipAmount = tipAmount;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof OrderResponse)) return false;
        OrderResponse other = (OrderResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$id = this.getId();
        java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        java.lang.Object this$tableId = this.getTableId();
        java.lang.Object other$tableId = other.getTableId();
        if (this$tableId == null ? other$tableId != null : !this$tableId.equals(other$tableId)) return false;
        java.lang.Object this$branchId = this.getBranchId();
        java.lang.Object other$branchId = other.getBranchId();
        if (this$branchId == null ? other$branchId != null : !this$branchId.equals(other$branchId)) return false;
        java.lang.Object this$orderNumber = this.getOrderNumber();
        java.lang.Object other$orderNumber = other.getOrderNumber();
        if (this$orderNumber == null ? other$orderNumber != null : !this$orderNumber.equals(other$orderNumber)) return false;
        java.lang.Object this$tableName = this.getTableName();
        java.lang.Object other$tableName = other.getTableName();
        if (this$tableName == null ? other$tableName != null : !this$tableName.equals(other$tableName)) return false;
        java.lang.Object this$branchName = this.getBranchName();
        java.lang.Object other$branchName = other.getBranchName();
        if (this$branchName == null ? other$branchName != null : !this$branchName.equals(other$branchName)) return false;
        java.lang.Object this$status = this.getStatus();
        java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        java.lang.Object this$totalAmount = this.getTotalAmount();
        java.lang.Object other$totalAmount = other.getTotalAmount();
        if (this$totalAmount == null ? other$totalAmount != null : !this$totalAmount.equals(other$totalAmount)) return false;
        java.lang.Object this$paymentMethod = this.getPaymentMethod();
        java.lang.Object other$paymentMethod = other.getPaymentMethod();
        if (this$paymentMethod == null ? other$paymentMethod != null : !this$paymentMethod.equals(other$paymentMethod)) return false;
        java.lang.Object this$tipAmount = this.getTipAmount();
        java.lang.Object other$tipAmount = other.getTipAmount();
        if (this$tipAmount == null ? other$tipAmount != null : !this$tipAmount.equals(other$tipAmount)) return false;
        java.lang.Object this$notes = this.getNotes();
        java.lang.Object other$notes = other.getNotes();
        if (this$notes == null ? other$notes != null : !this$notes.equals(other$notes)) return false;
        java.lang.Object this$items = this.getItems();
        java.lang.Object other$items = other.getItems();
        if (this$items == null ? other$items != null : !this$items.equals(other$items)) return false;
        java.lang.Object this$createdAt = this.getCreatedAt();
        java.lang.Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        java.lang.Object this$updatedAt = this.getUpdatedAt();
        java.lang.Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof OrderResponse;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        java.lang.Object $tableId = this.getTableId();
        result = result * PRIME + ($tableId == null ? 43 : $tableId.hashCode());
        java.lang.Object $branchId = this.getBranchId();
        result = result * PRIME + ($branchId == null ? 43 : $branchId.hashCode());
        java.lang.Object $orderNumber = this.getOrderNumber();
        result = result * PRIME + ($orderNumber == null ? 43 : $orderNumber.hashCode());
        java.lang.Object $tableName = this.getTableName();
        result = result * PRIME + ($tableName == null ? 43 : $tableName.hashCode());
        java.lang.Object $branchName = this.getBranchName();
        result = result * PRIME + ($branchName == null ? 43 : $branchName.hashCode());
        java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        java.lang.Object $totalAmount = this.getTotalAmount();
        result = result * PRIME + ($totalAmount == null ? 43 : $totalAmount.hashCode());
        java.lang.Object $paymentMethod = this.getPaymentMethod();
        result = result * PRIME + ($paymentMethod == null ? 43 : $paymentMethod.hashCode());
        java.lang.Object $tipAmount = this.getTipAmount();
        result = result * PRIME + ($tipAmount == null ? 43 : $tipAmount.hashCode());
        java.lang.Object $notes = this.getNotes();
        result = result * PRIME + ($notes == null ? 43 : $notes.hashCode());
        java.lang.Object $items = this.getItems();
        result = result * PRIME + ($items == null ? 43 : $items.hashCode());
        java.lang.Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        java.lang.Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "OrderResponse(id=" + this.getId() + ", orderNumber=" + this.getOrderNumber() + ", tableId=" + this.getTableId() + ", tableName=" + this.getTableName() + ", branchId=" + this.getBranchId() + ", branchName=" + this.getBranchName() + ", status=" + this.getStatus() + ", totalAmount=" + this.getTotalAmount() + ", paymentMethod=" + this.getPaymentMethod() + ", tipAmount=" + this.getTipAmount() + ", notes=" + this.getNotes() + ", items=" + this.getItems() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }
}
