package com.qrmenu.platform.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateBusinessRequest {
    @NotBlank(message = "Business name is required")
    @Size(max = 255, message = "Business name must not exceed 255 characters")
    private String name;
    @NotBlank(message = "Owner email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Owner email must not exceed 255 characters")
    private String ownerEmail;
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;


    public static class UpdateBusinessRequestBuilder {
        private String name;
        private String ownerEmail;
        private String phone;

        UpdateBusinessRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public UpdateBusinessRequest.UpdateBusinessRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateBusinessRequest.UpdateBusinessRequestBuilder ownerEmail(String ownerEmail) {
            this.ownerEmail = ownerEmail;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateBusinessRequest.UpdateBusinessRequestBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UpdateBusinessRequest build() {
            return new UpdateBusinessRequest(this.name, this.ownerEmail, this.phone);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "UpdateBusinessRequest.UpdateBusinessRequestBuilder(name=" + this.name + ", ownerEmail=" + this.ownerEmail + ", phone=" + this.phone + ")";
        }
    }

    public static UpdateBusinessRequest.UpdateBusinessRequestBuilder builder() {
        return new UpdateBusinessRequest.UpdateBusinessRequestBuilder();
    }

    public String getName() {
        return this.name;
    }

    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof UpdateBusinessRequest)) return false;
        UpdateBusinessRequest other = (UpdateBusinessRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$name = this.getName();
        java.lang.Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        java.lang.Object this$ownerEmail = this.getOwnerEmail();
        java.lang.Object other$ownerEmail = other.getOwnerEmail();
        if (this$ownerEmail == null ? other$ownerEmail != null : !this$ownerEmail.equals(other$ownerEmail)) return false;
        java.lang.Object this$phone = this.getPhone();
        java.lang.Object other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof UpdateBusinessRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        java.lang.Object $ownerEmail = this.getOwnerEmail();
        result = result * PRIME + ($ownerEmail == null ? 43 : $ownerEmail.hashCode());
        java.lang.Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "UpdateBusinessRequest(name=" + this.getName() + ", ownerEmail=" + this.getOwnerEmail() + ", phone=" + this.getPhone() + ")";
    }

    public UpdateBusinessRequest() {
    }

    public UpdateBusinessRequest(String name, String ownerEmail, String phone) {
        this.name = name;
        this.ownerEmail = ownerEmail;
        this.phone = phone;
    }
}
