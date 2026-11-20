package com.qrmenu.platform.table.dto;

import java.time.Instant;

public class RestaurantTableDto {
    private Long id;
    private Long businessId;
    private Long branchId;
    private String tableNumber;
    private String qrToken;
    private String qrUrl; // Absolute path to scan QR code
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;


    public static class RestaurantTableDtoBuilder {
        private Long id;
        private Long businessId;
        private Long branchId;
        private String tableNumber;
        private String qrToken;
        private String qrUrl;
        private boolean isActive;
        private Instant createdAt;
        private Instant updatedAt;

        RestaurantTableDtoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTableDto.RestaurantTableDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTableDto.RestaurantTableDtoBuilder businessId(Long businessId) {
            this.businessId = businessId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTableDto.RestaurantTableDtoBuilder branchId(Long branchId) {
            this.branchId = branchId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTableDto.RestaurantTableDtoBuilder tableNumber(String tableNumber) {
            this.tableNumber = tableNumber;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTableDto.RestaurantTableDtoBuilder qrToken(String qrToken) {
            this.qrToken = qrToken;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTableDto.RestaurantTableDtoBuilder qrUrl(String qrUrl) {
            this.qrUrl = qrUrl;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTableDto.RestaurantTableDtoBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTableDto.RestaurantTableDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTableDto.RestaurantTableDtoBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public RestaurantTableDto build() {
            return new RestaurantTableDto(this.id, this.businessId, this.branchId, this.tableNumber, this.qrToken, this.qrUrl, this.isActive, this.createdAt, this.updatedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "RestaurantTableDto.RestaurantTableDtoBuilder(id=" + this.id + ", businessId=" + this.businessId + ", branchId=" + this.branchId + ", tableNumber=" + this.tableNumber + ", qrToken=" + this.qrToken + ", qrUrl=" + this.qrUrl + ", isActive=" + this.isActive + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }

    public static RestaurantTableDto.RestaurantTableDtoBuilder builder() {
        return new RestaurantTableDto.RestaurantTableDtoBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Long getBusinessId() {
        return this.businessId;
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public String getTableNumber() {
        return this.tableNumber;
    }

    public String getQrToken() {
        return this.qrToken;
    }

    public String getQrUrl() {
        return this.qrUrl;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof RestaurantTableDto)) return false;
        RestaurantTableDto other = (RestaurantTableDto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.isActive() != other.isActive()) return false;
        java.lang.Object this$id = this.getId();
        java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        java.lang.Object this$businessId = this.getBusinessId();
        java.lang.Object other$businessId = other.getBusinessId();
        if (this$businessId == null ? other$businessId != null : !this$businessId.equals(other$businessId)) return false;
        java.lang.Object this$branchId = this.getBranchId();
        java.lang.Object other$branchId = other.getBranchId();
        if (this$branchId == null ? other$branchId != null : !this$branchId.equals(other$branchId)) return false;
        java.lang.Object this$tableNumber = this.getTableNumber();
        java.lang.Object other$tableNumber = other.getTableNumber();
        if (this$tableNumber == null ? other$tableNumber != null : !this$tableNumber.equals(other$tableNumber)) return false;
        java.lang.Object this$qrToken = this.getQrToken();
        java.lang.Object other$qrToken = other.getQrToken();
        if (this$qrToken == null ? other$qrToken != null : !this$qrToken.equals(other$qrToken)) return false;
        java.lang.Object this$qrUrl = this.getQrUrl();
        java.lang.Object other$qrUrl = other.getQrUrl();
        if (this$qrUrl == null ? other$qrUrl != null : !this$qrUrl.equals(other$qrUrl)) return false;
        java.lang.Object this$createdAt = this.getCreatedAt();
        java.lang.Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        java.lang.Object this$updatedAt = this.getUpdatedAt();
        java.lang.Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof RestaurantTableDto;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isActive() ? 79 : 97);
        java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        java.lang.Object $businessId = this.getBusinessId();
        result = result * PRIME + ($businessId == null ? 43 : $businessId.hashCode());
        java.lang.Object $branchId = this.getBranchId();
        result = result * PRIME + ($branchId == null ? 43 : $branchId.hashCode());
        java.lang.Object $tableNumber = this.getTableNumber();
        result = result * PRIME + ($tableNumber == null ? 43 : $tableNumber.hashCode());
        java.lang.Object $qrToken = this.getQrToken();
        result = result * PRIME + ($qrToken == null ? 43 : $qrToken.hashCode());
        java.lang.Object $qrUrl = this.getQrUrl();
        result = result * PRIME + ($qrUrl == null ? 43 : $qrUrl.hashCode());
        java.lang.Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        java.lang.Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "RestaurantTableDto(id=" + this.getId() + ", businessId=" + this.getBusinessId() + ", branchId=" + this.getBranchId() + ", tableNumber=" + this.getTableNumber() + ", qrToken=" + this.getQrToken() + ", qrUrl=" + this.getQrUrl() + ", isActive=" + this.isActive() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public RestaurantTableDto() {
    }

    public RestaurantTableDto(Long id, Long businessId, Long branchId, String tableNumber, String qrToken, String qrUrl, boolean isActive, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.businessId = businessId;
        this.branchId = branchId;
        this.tableNumber = tableNumber;
        this.qrToken = qrToken;
        this.qrUrl = qrUrl;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
