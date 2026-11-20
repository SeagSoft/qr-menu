package com.qrmenu.platform.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateBranchRequest {
    @NotNull(message = "Business ID is required")
    private Long businessId;
    @NotBlank(message = "Branch name is required")
    @Size(max = 255, message = "Branch name must not exceed 255 characters")
    private String name;
    private String address;
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;


    public static class CreateBranchRequestBuilder {
        private Long businessId;
        private String name;
        private String address;
        private String phone;

        CreateBranchRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public CreateBranchRequest.CreateBranchRequestBuilder businessId(Long businessId) {
            this.businessId = businessId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateBranchRequest.CreateBranchRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateBranchRequest.CreateBranchRequestBuilder address(String address) {
            this.address = address;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateBranchRequest.CreateBranchRequestBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public CreateBranchRequest build() {
            return new CreateBranchRequest(this.businessId, this.name, this.address, this.phone);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "CreateBranchRequest.CreateBranchRequestBuilder(businessId=" + this.businessId + ", name=" + this.name + ", address=" + this.address + ", phone=" + this.phone + ")";
        }
    }

    public static CreateBranchRequest.CreateBranchRequestBuilder builder() {
        return new CreateBranchRequest.CreateBranchRequestBuilder();
    }

    public Long getBusinessId() {
        return this.businessId;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateBranchRequest)) return false;
        CreateBranchRequest other = (CreateBranchRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$businessId = this.getBusinessId();
        java.lang.Object other$businessId = other.getBusinessId();
        if (this$businessId == null ? other$businessId != null : !this$businessId.equals(other$businessId)) return false;
        java.lang.Object this$name = this.getName();
        java.lang.Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        java.lang.Object this$address = this.getAddress();
        java.lang.Object other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) return false;
        java.lang.Object this$phone = this.getPhone();
        java.lang.Object other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof CreateBranchRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $businessId = this.getBusinessId();
        result = result * PRIME + ($businessId == null ? 43 : $businessId.hashCode());
        java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        java.lang.Object $address = this.getAddress();
        result = result * PRIME + ($address == null ? 43 : $address.hashCode());
        java.lang.Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "CreateBranchRequest(businessId=" + this.getBusinessId() + ", name=" + this.getName() + ", address=" + this.getAddress() + ", phone=" + this.getPhone() + ")";
    }

    public CreateBranchRequest() {
    }

    public CreateBranchRequest(Long businessId, String name, String address, String phone) {
        this.businessId = businessId;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
