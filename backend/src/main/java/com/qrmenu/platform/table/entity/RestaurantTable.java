package com.qrmenu.platform.table.entity;

import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_table", uniqueConstraints = {@UniqueConstraint(name = "uq_branch_table", columnNames = {"branch_id", "table_number"})})
public class RestaurantTable extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
    @Column(name = "table_number", nullable = false)
    private String tableNumber;
    @Column(name = "qr_token", nullable = false, unique = true)
    private String qrToken;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    private static boolean $default$isActive() {
        return true;
    }


    public static class RestaurantTableBuilder {
        private Long id;
        private Business business;
        private Branch branch;
        private String tableNumber;
        private String qrToken;
        private boolean isActive$set;
        private boolean isActive$value;

        RestaurantTableBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTable.RestaurantTableBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTable.RestaurantTableBuilder business(Business business) {
            this.business = business;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTable.RestaurantTableBuilder branch(Branch branch) {
            this.branch = branch;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTable.RestaurantTableBuilder tableNumber(String tableNumber) {
            this.tableNumber = tableNumber;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTable.RestaurantTableBuilder qrToken(String qrToken) {
            this.qrToken = qrToken;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RestaurantTable.RestaurantTableBuilder isActive(boolean isActive) {
            this.isActive$value = isActive;
            isActive$set = true;
            return this;
        }

        public RestaurantTable build() {
            boolean isActive$value = this.isActive$value;
            if (!this.isActive$set) isActive$value = RestaurantTable.$default$isActive();
            return new RestaurantTable(this.id, this.business, this.branch, this.tableNumber, this.qrToken, isActive$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "RestaurantTable.RestaurantTableBuilder(id=" + this.id + ", business=" + this.business + ", branch=" + this.branch + ", tableNumber=" + this.tableNumber + ", qrToken=" + this.qrToken + ", isActive$value=" + this.isActive$value + ")";
        }
    }

    public static RestaurantTable.RestaurantTableBuilder builder() {
        return new RestaurantTable.RestaurantTableBuilder();
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

    public String getTableNumber() {
        return this.tableNumber;
    }

    public String getQrToken() {
        return this.qrToken;
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

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public RestaurantTable() {
        this.isActive = RestaurantTable.$default$isActive();
    }

    public RestaurantTable(Long id, Business business, Branch branch, String tableNumber, String qrToken, boolean isActive) {
        this.id = id;
        this.business = business;
        this.branch = branch;
        this.tableNumber = tableNumber;
        this.qrToken = qrToken;
        this.isActive = isActive;
    }
}
