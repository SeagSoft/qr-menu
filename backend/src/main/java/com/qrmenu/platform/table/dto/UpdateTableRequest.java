package com.qrmenu.platform.table.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateTableRequest {
    @NotBlank(message = "Table number is required")
    @Size(max = 50, message = "Table number must not exceed 50 characters")
    private String tableNumber;


    public static class UpdateTableRequestBuilder {
        private String tableNumber;

        UpdateTableRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public UpdateTableRequest.UpdateTableRequestBuilder tableNumber(String tableNumber) {
            this.tableNumber = tableNumber;
            return this;
        }

        public UpdateTableRequest build() {
            return new UpdateTableRequest(this.tableNumber);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "UpdateTableRequest.UpdateTableRequestBuilder(tableNumber=" + this.tableNumber + ")";
        }
    }

    public static UpdateTableRequest.UpdateTableRequestBuilder builder() {
        return new UpdateTableRequest.UpdateTableRequestBuilder();
    }

    public String getTableNumber() {
        return this.tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof UpdateTableRequest)) return false;
        UpdateTableRequest other = (UpdateTableRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$tableNumber = this.getTableNumber();
        java.lang.Object other$tableNumber = other.getTableNumber();
        if (this$tableNumber == null ? other$tableNumber != null : !this$tableNumber.equals(other$tableNumber)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof UpdateTableRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $tableNumber = this.getTableNumber();
        result = result * PRIME + ($tableNumber == null ? 43 : $tableNumber.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "UpdateTableRequest(tableNumber=" + this.getTableNumber() + ")";
    }

    public UpdateTableRequest() {
    }

    public UpdateTableRequest(String tableNumber) {
        this.tableNumber = tableNumber;
    }
}
