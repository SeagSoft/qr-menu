package com.qrmenu.platform.auth.dto;

import com.qrmenu.platform.auth.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterStaffRequest {
    @NotNull(message = "Business ID is required")
    private Long businessId;
    private Long branchId;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 100, message = "Password/PIN must be between 4 and 100 characters")
    private String password;
    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;
    @NotNull(message = "Role is required")
    private Role role;


    public static class RegisterStaffRequestBuilder {
        private Long businessId;
        private Long branchId;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private Role role;

        RegisterStaffRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public RegisterStaffRequest.RegisterStaffRequestBuilder businessId(Long businessId) {
            this.businessId = businessId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RegisterStaffRequest.RegisterStaffRequestBuilder branchId(Long branchId) {
            this.branchId = branchId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RegisterStaffRequest.RegisterStaffRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RegisterStaffRequest.RegisterStaffRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RegisterStaffRequest.RegisterStaffRequestBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RegisterStaffRequest.RegisterStaffRequestBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RegisterStaffRequest.RegisterStaffRequestBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public RegisterStaffRequest build() {
            return new RegisterStaffRequest(this.businessId, this.branchId, this.email, this.password, this.firstName, this.lastName, this.role);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "RegisterStaffRequest.RegisterStaffRequestBuilder(businessId=" + this.businessId + ", branchId=" + this.branchId + ", email=" + this.email + ", password=" + this.password + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", role=" + this.role + ")";
        }
    }

    public static RegisterStaffRequest.RegisterStaffRequestBuilder builder() {
        return new RegisterStaffRequest.RegisterStaffRequestBuilder();
    }

    public Long getBusinessId() {
        return this.businessId;
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Role getRole() {
        return this.role;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof RegisterStaffRequest)) return false;
        RegisterStaffRequest other = (RegisterStaffRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$businessId = this.getBusinessId();
        java.lang.Object other$businessId = other.getBusinessId();
        if (this$businessId == null ? other$businessId != null : !this$businessId.equals(other$businessId)) return false;
        java.lang.Object this$branchId = this.getBranchId();
        java.lang.Object other$branchId = other.getBranchId();
        if (this$branchId == null ? other$branchId != null : !this$branchId.equals(other$branchId)) return false;
        java.lang.Object this$email = this.getEmail();
        java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        java.lang.Object this$password = this.getPassword();
        java.lang.Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        java.lang.Object this$firstName = this.getFirstName();
        java.lang.Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        java.lang.Object this$lastName = this.getLastName();
        java.lang.Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        java.lang.Object this$role = this.getRole();
        java.lang.Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof RegisterStaffRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $businessId = this.getBusinessId();
        result = result * PRIME + ($businessId == null ? 43 : $businessId.hashCode());
        java.lang.Object $branchId = this.getBranchId();
        result = result * PRIME + ($branchId == null ? 43 : $branchId.hashCode());
        java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        java.lang.Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        java.lang.Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        java.lang.Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        java.lang.Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "RegisterStaffRequest(businessId=" + this.getBusinessId() + ", branchId=" + this.getBranchId() + ", email=" + this.getEmail() + ", password=" + this.getPassword() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", role=" + this.getRole() + ")";
    }

    public RegisterStaffRequest() {
    }

    public RegisterStaffRequest(Long businessId, Long branchId, String email, String password, String firstName, String lastName, Role role) {
        this.businessId = businessId;
        this.branchId = branchId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
