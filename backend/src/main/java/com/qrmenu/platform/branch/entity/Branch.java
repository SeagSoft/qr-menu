package com.qrmenu.platform.branch.entity;

import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "branch")
public class Branch extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    private static boolean $default$isActive() {
        return true;
    }


    public static class BranchBuilder {
        private Long id;
        private Business business;
        private String name;
        private String address;
        private String phone;
        private boolean isActive$set;
        private boolean isActive$value;

        BranchBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Branch.BranchBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Branch.BranchBuilder business(Business business) {
            this.business = business;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Branch.BranchBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Branch.BranchBuilder address(String address) {
            this.address = address;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Branch.BranchBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Branch.BranchBuilder isActive(boolean isActive) {
            this.isActive$value = isActive;
            isActive$set = true;
            return this;
        }

        public Branch build() {
            boolean isActive$value = this.isActive$value;
            if (!this.isActive$set) isActive$value = Branch.$default$isActive();
            return new Branch(this.id, this.business, this.name, this.address, this.phone, isActive$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Branch.BranchBuilder(id=" + this.id + ", business=" + this.business + ", name=" + this.name + ", address=" + this.address + ", phone=" + this.phone + ", isActive$value=" + this.isActive$value + ")";
        }
    }

    public static Branch.BranchBuilder builder() {
        return new Branch.BranchBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Business getBusiness() {
        return this.business;
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

    public boolean isActive() {
        return this.isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBusiness(Business business) {
        this.business = business;
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

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Branch() {
        this.isActive = Branch.$default$isActive();
    }

    public Branch(Long id, Business business, String name, String address, String phone, boolean isActive) {
        this.id = id;
        this.business = business;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
    }
}
