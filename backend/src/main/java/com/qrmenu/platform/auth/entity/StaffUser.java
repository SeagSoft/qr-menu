package com.qrmenu.platform.auth.entity;

import com.qrmenu.platform.auth.model.Role;
import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "staff_user")
public class StaffUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public String getFullName() {
        return (firstName != null ? firstName : "") + (lastName != null ? " " + lastName : "");
    }

    private static boolean $default$isActive() {
        return true;
    }


    public static class StaffUserBuilder {
        private Long id;
        private Business business;
        private Branch branch;
        private String email;
        private String passwordHash;
        private String firstName;
        private String lastName;
        private Role role;
        private boolean isActive$set;
        private boolean isActive$value;

        StaffUserBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public StaffUser.StaffUserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUser.StaffUserBuilder business(Business business) {
            this.business = business;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUser.StaffUserBuilder branch(Branch branch) {
            this.branch = branch;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUser.StaffUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUser.StaffUserBuilder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUser.StaffUserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUser.StaffUserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUser.StaffUserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public StaffUser.StaffUserBuilder isActive(boolean isActive) {
            this.isActive$value = isActive;
            isActive$set = true;
            return this;
        }

        public StaffUser build() {
            boolean isActive$value = this.isActive$value;
            if (!this.isActive$set) isActive$value = StaffUser.$default$isActive();
            return new StaffUser(this.id, this.business, this.branch, this.email, this.passwordHash, this.firstName, this.lastName, this.role, isActive$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "StaffUser.StaffUserBuilder(id=" + this.id + ", business=" + this.business + ", branch=" + this.branch + ", email=" + this.email + ", passwordHash=" + this.passwordHash + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", role=" + this.role + ", isActive$value=" + this.isActive$value + ")";
        }
    }

    public static StaffUser.StaffUserBuilder builder() {
        return new StaffUser.StaffUserBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Business getBusiness() {
        return this.business;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPasswordHash() {
        return this.passwordHash;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public StaffUser() {
        this.isActive = StaffUser.$default$isActive();
    }

    public StaffUser(Long id, Business business, Branch branch, String email, String passwordHash, String firstName, String lastName, Role role, boolean isActive) {
        this.id = id;
        this.business = business;
        this.branch = branch;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isActive = isActive;
    }
}
