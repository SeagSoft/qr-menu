package com.qrmenu.platform.ordering.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
    private Long id;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String selectedVariantName;
    private String selectedOptionsText;
    private String notes;

    OrderItemResponse(Long id, String productName, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice, String selectedVariantName, String selectedOptionsText, String notes) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.selectedVariantName = selectedVariantName;
        this.selectedOptionsText = selectedOptionsText;
        this.notes = notes;
    }


    public static class OrderItemResponseBuilder {
        private Long id;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private String selectedVariantName;
        private String selectedOptionsText;
        private String notes;

        OrderItemResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public OrderItemResponse.OrderItemResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItemResponse.OrderItemResponseBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItemResponse.OrderItemResponseBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItemResponse.OrderItemResponseBuilder unitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItemResponse.OrderItemResponseBuilder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItemResponse.OrderItemResponseBuilder selectedVariantName(String selectedVariantName) {
            this.selectedVariantName = selectedVariantName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItemResponse.OrderItemResponseBuilder selectedOptionsText(String selectedOptionsText) {
            this.selectedOptionsText = selectedOptionsText;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItemResponse.OrderItemResponseBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public OrderItemResponse build() {
            return new OrderItemResponse(this.id, this.productName, this.quantity, this.unitPrice, this.totalPrice, this.selectedVariantName, this.selectedOptionsText, this.notes);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "OrderItemResponse.OrderItemResponseBuilder(id=" + this.id + ", productName=" + this.productName + ", quantity=" + this.quantity + ", unitPrice=" + this.unitPrice + ", totalPrice=" + this.totalPrice + ", selectedVariantName=" + this.selectedVariantName + ", selectedOptionsText=" + this.selectedOptionsText + ", notes=" + this.notes + ")";
        }
    }

    public static OrderItemResponse.OrderItemResponseBuilder builder() {
        return new OrderItemResponse.OrderItemResponseBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getProductName() {
        return this.productName;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public String getSelectedVariantName() {
        return this.selectedVariantName;
    }

    public String getSelectedOptionsText() {
        return this.selectedOptionsText;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setSelectedVariantName(String selectedVariantName) {
        this.selectedVariantName = selectedVariantName;
    }

    public void setSelectedOptionsText(String selectedOptionsText) {
        this.selectedOptionsText = selectedOptionsText;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof OrderItemResponse)) return false;
        OrderItemResponse other = (OrderItemResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$id = this.getId();
        java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        java.lang.Object this$quantity = this.getQuantity();
        java.lang.Object other$quantity = other.getQuantity();
        if (this$quantity == null ? other$quantity != null : !this$quantity.equals(other$quantity)) return false;
        java.lang.Object this$productName = this.getProductName();
        java.lang.Object other$productName = other.getProductName();
        if (this$productName == null ? other$productName != null : !this$productName.equals(other$productName)) return false;
        java.lang.Object this$unitPrice = this.getUnitPrice();
        java.lang.Object other$unitPrice = other.getUnitPrice();
        if (this$unitPrice == null ? other$unitPrice != null : !this$unitPrice.equals(other$unitPrice)) return false;
        java.lang.Object this$totalPrice = this.getTotalPrice();
        java.lang.Object other$totalPrice = other.getTotalPrice();
        if (this$totalPrice == null ? other$totalPrice != null : !this$totalPrice.equals(other$totalPrice)) return false;
        java.lang.Object this$selectedVariantName = this.getSelectedVariantName();
        java.lang.Object other$selectedVariantName = other.getSelectedVariantName();
        if (this$selectedVariantName == null ? other$selectedVariantName != null : !this$selectedVariantName.equals(other$selectedVariantName)) return false;
        java.lang.Object this$selectedOptionsText = this.getSelectedOptionsText();
        java.lang.Object other$selectedOptionsText = other.getSelectedOptionsText();
        if (this$selectedOptionsText == null ? other$selectedOptionsText != null : !this$selectedOptionsText.equals(other$selectedOptionsText)) return false;
        java.lang.Object this$notes = this.getNotes();
        java.lang.Object other$notes = other.getNotes();
        if (this$notes == null ? other$notes != null : !this$notes.equals(other$notes)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof OrderItemResponse;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        java.lang.Object $quantity = this.getQuantity();
        result = result * PRIME + ($quantity == null ? 43 : $quantity.hashCode());
        java.lang.Object $productName = this.getProductName();
        result = result * PRIME + ($productName == null ? 43 : $productName.hashCode());
        java.lang.Object $unitPrice = this.getUnitPrice();
        result = result * PRIME + ($unitPrice == null ? 43 : $unitPrice.hashCode());
        java.lang.Object $totalPrice = this.getTotalPrice();
        result = result * PRIME + ($totalPrice == null ? 43 : $totalPrice.hashCode());
        java.lang.Object $selectedVariantName = this.getSelectedVariantName();
        result = result * PRIME + ($selectedVariantName == null ? 43 : $selectedVariantName.hashCode());
        java.lang.Object $selectedOptionsText = this.getSelectedOptionsText();
        result = result * PRIME + ($selectedOptionsText == null ? 43 : $selectedOptionsText.hashCode());
        java.lang.Object $notes = this.getNotes();
        result = result * PRIME + ($notes == null ? 43 : $notes.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "OrderItemResponse(id=" + this.getId() + ", productName=" + this.getProductName() + ", quantity=" + this.getQuantity() + ", unitPrice=" + this.getUnitPrice() + ", totalPrice=" + this.getTotalPrice() + ", selectedVariantName=" + this.getSelectedVariantName() + ", selectedOptionsText=" + this.getSelectedOptionsText() + ", notes=" + this.getNotes() + ")";
    }
}
