package com.qrmenu.platform.ordering.dto;

import java.math.BigDecimal;
import java.util.List;

public class SalesReportResponse {
    private Long branchId;
    private BigDecimal grossRevenue;
    private int orderCount;
    private BigDecimal avgOrderValue;
    private int activeOrderCount;
    private List<OrderResponse> orders;


    public static class SalesReportResponseBuilder {
        private Long branchId;
        private BigDecimal grossRevenue;
        private int orderCount;
        private BigDecimal avgOrderValue;
        private int activeOrderCount;
        private List<OrderResponse> orders;

        SalesReportResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public SalesReportResponse.SalesReportResponseBuilder branchId(Long branchId) {
            this.branchId = branchId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public SalesReportResponse.SalesReportResponseBuilder grossRevenue(BigDecimal grossRevenue) {
            this.grossRevenue = grossRevenue;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public SalesReportResponse.SalesReportResponseBuilder orderCount(int orderCount) {
            this.orderCount = orderCount;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public SalesReportResponse.SalesReportResponseBuilder avgOrderValue(BigDecimal avgOrderValue) {
            this.avgOrderValue = avgOrderValue;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public SalesReportResponse.SalesReportResponseBuilder activeOrderCount(int activeOrderCount) {
            this.activeOrderCount = activeOrderCount;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public SalesReportResponse.SalesReportResponseBuilder orders(List<OrderResponse> orders) {
            this.orders = orders;
            return this;
        }

        public SalesReportResponse build() {
            return new SalesReportResponse(this.branchId, this.grossRevenue, this.orderCount, this.avgOrderValue, this.activeOrderCount, this.orders);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "SalesReportResponse.SalesReportResponseBuilder(branchId=" + this.branchId + ", grossRevenue=" + this.grossRevenue + ", orderCount=" + this.orderCount + ", avgOrderValue=" + this.avgOrderValue + ", activeOrderCount=" + this.activeOrderCount + ", orders=" + this.orders + ")";
        }
    }

    public static SalesReportResponse.SalesReportResponseBuilder builder() {
        return new SalesReportResponse.SalesReportResponseBuilder();
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public BigDecimal getGrossRevenue() {
        return this.grossRevenue;
    }

    public int getOrderCount() {
        return this.orderCount;
    }

    public BigDecimal getAvgOrderValue() {
        return this.avgOrderValue;
    }

    public int getActiveOrderCount() {
        return this.activeOrderCount;
    }

    public List<OrderResponse> getOrders() {
        return this.orders;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public void setGrossRevenue(BigDecimal grossRevenue) {
        this.grossRevenue = grossRevenue;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public void setAvgOrderValue(BigDecimal avgOrderValue) {
        this.avgOrderValue = avgOrderValue;
    }

    public void setActiveOrderCount(int activeOrderCount) {
        this.activeOrderCount = activeOrderCount;
    }

    public void setOrders(List<OrderResponse> orders) {
        this.orders = orders;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SalesReportResponse)) return false;
        SalesReportResponse other = (SalesReportResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getOrderCount() != other.getOrderCount()) return false;
        if (this.getActiveOrderCount() != other.getActiveOrderCount()) return false;
        java.lang.Object this$branchId = this.getBranchId();
        java.lang.Object other$branchId = other.getBranchId();
        if (this$branchId == null ? other$branchId != null : !this$branchId.equals(other$branchId)) return false;
        java.lang.Object this$grossRevenue = this.getGrossRevenue();
        java.lang.Object other$grossRevenue = other.getGrossRevenue();
        if (this$grossRevenue == null ? other$grossRevenue != null : !this$grossRevenue.equals(other$grossRevenue)) return false;
        java.lang.Object this$avgOrderValue = this.getAvgOrderValue();
        java.lang.Object other$avgOrderValue = other.getAvgOrderValue();
        if (this$avgOrderValue == null ? other$avgOrderValue != null : !this$avgOrderValue.equals(other$avgOrderValue)) return false;
        java.lang.Object this$orders = this.getOrders();
        java.lang.Object other$orders = other.getOrders();
        if (this$orders == null ? other$orders != null : !this$orders.equals(other$orders)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof SalesReportResponse;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getOrderCount();
        result = result * PRIME + this.getActiveOrderCount();
        java.lang.Object $branchId = this.getBranchId();
        result = result * PRIME + ($branchId == null ? 43 : $branchId.hashCode());
        java.lang.Object $grossRevenue = this.getGrossRevenue();
        result = result * PRIME + ($grossRevenue == null ? 43 : $grossRevenue.hashCode());
        java.lang.Object $avgOrderValue = this.getAvgOrderValue();
        result = result * PRIME + ($avgOrderValue == null ? 43 : $avgOrderValue.hashCode());
        java.lang.Object $orders = this.getOrders();
        result = result * PRIME + ($orders == null ? 43 : $orders.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "SalesReportResponse(branchId=" + this.getBranchId() + ", grossRevenue=" + this.getGrossRevenue() + ", orderCount=" + this.getOrderCount() + ", avgOrderValue=" + this.getAvgOrderValue() + ", activeOrderCount=" + this.getActiveOrderCount() + ", orders=" + this.getOrders() + ")";
    }

    public SalesReportResponse() {
    }

    public SalesReportResponse(Long branchId, BigDecimal grossRevenue, int orderCount, BigDecimal avgOrderValue, int activeOrderCount, List<OrderResponse> orders) {
        this.branchId = branchId;
        this.grossRevenue = grossRevenue;
        this.orderCount = orderCount;
        this.avgOrderValue = avgOrderValue;
        this.activeOrderCount = activeOrderCount;
        this.orders = orders;
    }
}
