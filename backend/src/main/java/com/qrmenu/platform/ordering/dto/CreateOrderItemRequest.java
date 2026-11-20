package com.qrmenu.platform.ordering.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateOrderItemRequest {
    @NotBlank(message = "Product name is required")
    private String productName;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    @NotNull(message = "Unit price is required")
    private BigDecimal unitPrice;
    private String selectedVariantName;
    private String selectedOptionsText;
    private String notes;

    public CreateOrderItemRequest() {
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

    public String getSelectedVariantName() {
        return this.selectedVariantName;
    }

    public String getSelectedOptionsText() {
        return this.selectedOptionsText;
    }

    public String getNotes() {
        return this.notes;
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
        if (!(o instanceof CreateOrderItemRequest)) return false;
        CreateOrderItemRequest other = (CreateOrderItemRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$quantity = this.getQuantity();
        java.lang.Object other$quantity = other.getQuantity();
        if (this$quantity == null ? other$quantity != null : !this$quantity.equals(other$quantity)) return false;
        java.lang.Object this$productName = this.getProductName();
        java.lang.Object other$productName = other.getProductName();
        if (this$productName == null ? other$productName != null : !this$productName.equals(other$productName)) return false;
        java.lang.Object this$unitPrice = this.getUnitPrice();
        java.lang.Object other$unitPrice = other.getUnitPrice();
        if (this$unitPrice == null ? other$unitPrice != null : !this$unitPrice.equals(other$unitPrice)) return false;
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
        return other instanceof CreateOrderItemRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $quantity = this.getQuantity();
        result = result * PRIME + ($quantity == null ? 43 : $quantity.hashCode());
        java.lang.Object $productName = this.getProductName();
        result = result * PRIME + ($productName == null ? 43 : $productName.hashCode());
        java.lang.Object $unitPrice = this.getUnitPrice();
        result = result * PRIME + ($unitPrice == null ? 43 : $unitPrice.hashCode());
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
        return "CreateOrderItemRequest(productName=" + this.getProductName() + ", quantity=" + this.getQuantity() + ", unitPrice=" + this.getUnitPrice() + ", selectedVariantName=" + this.getSelectedVariantName() + ", selectedOptionsText=" + this.getSelectedOptionsText() + ", notes=" + this.getNotes() + ")";
    }
}
