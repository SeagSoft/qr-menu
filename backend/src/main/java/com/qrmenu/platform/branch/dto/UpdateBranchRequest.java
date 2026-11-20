package com.qrmenu.platform.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateBranchRequest {
    @NotBlank(message = "Branch name is required")
    @Size(max = 255, message = "Branch name must not exceed 255 characters")
    private String name;
    private String address;
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;


    public static class UpdateBranchRequestBuilder {
        private String name;
        private String address;
        private String phone;

        UpdateBranchRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public UpdateBranchRequest.UpdateBranchRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateBranchRequest.UpdateBranchRequestBuilder address(String address) {
            this.address = address;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateBranchRequest.UpdateBranchRequestBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UpdateBranchRequest build() {
            return new UpdateBranchRequest(this.name, this.address, this.phone);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "UpdateBranchRequest.UpdateBranchRequestBuilder(name=" + this.name + ", address=" + this.address + ", phone=" + this.phone + ")";
        }
    }

    public static UpdateBranchRequest.UpdateBranchRequestBuilder builder() {
        return new UpdateBranchRequest.UpdateBranchRequestBuilder();
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
        if (!(o instanceof UpdateBranchRequest)) return false;
        UpdateBranchRequest other = (UpdateBranchRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
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
        return other instanceof UpdateBranchRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
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
        return "UpdateBranchRequest(name=" + this.getName() + ", address=" + this.getAddress() + ", phone=" + this.getPhone() + ")";
    }

    public UpdateBranchRequest() {
    }

    public UpdateBranchRequest(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
