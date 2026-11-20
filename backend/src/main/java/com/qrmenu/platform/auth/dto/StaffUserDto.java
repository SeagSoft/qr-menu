package com.qrmenu.platform.auth.dto;

import com.qrmenu.platform.auth.model.Role;
import java.time.Instant;

public class StaffUserDto {
    private Long id;
    private Long businessId;
    private Long branchId;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;


    public static class StaffUserDtoBuilder {
        private Long id;
        private Long businessId;
        private Long branchId;
        private String email;
        private String firstName;
        private String lastName;
        private Role role;
        private boolean isActive;
        private Instant createdAt;
        private Instant updatedAt;

        StaffUserDtoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public StaffUserDto.StaffUserDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUserDto.StaffUserDtoBuilder businessId(Long businessId) {
            this.businessId = businessId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUserDto.StaffUserDtoBuilder branchId(Long branchId) {
            this.branchId = branchId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUserDto.StaffUserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUserDto.StaffUserDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUserDto.StaffUserDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUserDto.StaffUserDtoBuilder role(Role role) {
            this.role = role;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUserDto.StaffUserDtoBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUserDto.StaffUserDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUserDto.StaffUserDtoBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public StaffUserDto build() {
            return new StaffUserDto(this.id, this.businessId, this.branchId, this.email, this.firstName, this.lastName, this.role, this.isActive, this.createdAt, this.updatedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "StaffUserDto.StaffUserDtoBuilder(id=" + this.id + ", businessId=" + this.businessId + ", branchId=" + this.branchId + ", email=" + this.email + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", role=" + this.role + ", isActive=" + this.isActive + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }

    public static StaffUserDto.StaffUserDtoBuilder builder() {
        return new StaffUserDto.StaffUserDtoBuilder();
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

    public String getEmail() {
        return this.email;
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

    public void setEmail(String email) {
        this.email = email;
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
        if (!(o instanceof StaffUserDto)) return false;
        StaffUserDto other = (StaffUserDto) o;
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
        java.lang.Object this$email = this.getEmail();
        java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        java.lang.Object this$firstName = this.getFirstName();
        java.lang.Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        java.lang.Object this$lastName = this.getLastName();
        java.lang.Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        java.lang.Object this$role = this.getRole();
        java.lang.Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        java.lang.Object this$createdAt = this.getCreatedAt();
        java.lang.Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        java.lang.Object this$updatedAt = this.getUpdatedAt();
        java.lang.Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof StaffUserDto;
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
        java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        java.lang.Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        java.lang.Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        java.lang.Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        java.lang.Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        java.lang.Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "StaffUserDto(id=" + this.getId() + ", businessId=" + this.getBusinessId() + ", branchId=" + this.getBranchId() + ", email=" + this.getEmail() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", role=" + this.getRole() + ", isActive=" + this.isActive() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public StaffUserDto() {
    }

    public StaffUserDto(Long id, Long businessId, Long branchId, String email, String firstName, String lastName, Role role, boolean isActive, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.businessId = businessId;
        this.branchId = branchId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
