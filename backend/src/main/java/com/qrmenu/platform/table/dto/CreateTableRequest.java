package com.qrmenu.platform.table.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTableRequest {
    @NotNull(message = "Branch ID is required")
    private Long branchId;
    @NotBlank(message = "Table number is required")
    @Size(max = 50, message = "Table number must not exceed 50 characters")
    private String tableNumber;


    public static class CreateTableRequestBuilder {
        private Long branchId;
        private String tableNumber;

        CreateTableRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public CreateTableRequest.CreateTableRequestBuilder branchId(Long branchId) {
            this.branchId = branchId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateTableRequest.CreateTableRequestBuilder tableNumber(String tableNumber) {
            this.tableNumber = tableNumber;
            return this;
        }

        public CreateTableRequest build() {
            return new CreateTableRequest(this.branchId, this.tableNumber);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "CreateTableRequest.CreateTableRequestBuilder(branchId=" + this.branchId + ", tableNumber=" + this.tableNumber + ")";
        }
    }

    public static CreateTableRequest.CreateTableRequestBuilder builder() {
        return new CreateTableRequest.CreateTableRequestBuilder();
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public String getTableNumber() {
        return this.tableNumber;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateTableRequest)) return false;
        CreateTableRequest other = (CreateTableRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$branchId = this.getBranchId();
        java.lang.Object other$branchId = other.getBranchId();
        if (this$branchId == null ? other$branchId != null : !this$branchId.equals(other$branchId)) return false;
        java.lang.Object this$tableNumber = this.getTableNumber();
        java.lang.Object other$tableNumber = other.getTableNumber();
        if (this$tableNumber == null ? other$tableNumber != null : !this$tableNumber.equals(other$tableNumber)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof CreateTableRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $branchId = this.getBranchId();
        result = result * PRIME + ($branchId == null ? 43 : $branchId.hashCode());
        java.lang.Object $tableNumber = this.getTableNumber();
        result = result * PRIME + ($tableNumber == null ? 43 : $tableNumber.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "CreateTableRequest(branchId=" + this.getBranchId() + ", tableNumber=" + this.getTableNumber() + ")";
    }

    public CreateTableRequest() {
    }

    public CreateTableRequest(Long branchId, String tableNumber) {
        this.branchId = branchId;
        this.tableNumber = tableNumber;
    }
}
