package com.qrmenu.platform.ordering.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

public class CreateOrderRequest {
    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    private List<CreateOrderItemRequest> items;
    private String paymentMethod;
    private BigDecimal tipAmount;
    private String notes;

    public CreateOrderRequest() {
    }

    public List<CreateOrderItemRequest> getItems() {
        return this.items;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public BigDecimal getTipAmount() {
        return this.tipAmount;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setItems(List<CreateOrderItemRequest> items) {
        this.items = items;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setTipAmount(BigDecimal tipAmount) {
        this.tipAmount = tipAmount;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateOrderRequest)) return false;
        CreateOrderRequest other = (CreateOrderRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$items = this.getItems();
        java.lang.Object other$items = other.getItems();
        if (this$items == null ? other$items != null : !this$items.equals(other$items)) return false;
        java.lang.Object this$paymentMethod = this.getPaymentMethod();
        java.lang.Object other$paymentMethod = other.getPaymentMethod();
        if (this$paymentMethod == null ? other$paymentMethod != null : !this$paymentMethod.equals(other$paymentMethod)) return false;
        java.lang.Object this$tipAmount = this.getTipAmount();
        java.lang.Object other$tipAmount = other.getTipAmount();
        if (this$tipAmount == null ? other$tipAmount != null : !this$tipAmount.equals(other$tipAmount)) return false;
        java.lang.Object this$notes = this.getNotes();
        java.lang.Object other$notes = other.getNotes();
        if (this$notes == null ? other$notes != null : !this$notes.equals(other$notes)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof CreateOrderRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $items = this.getItems();
        result = result * PRIME + ($items == null ? 43 : $items.hashCode());
        java.lang.Object $paymentMethod = this.getPaymentMethod();
        result = result * PRIME + ($paymentMethod == null ? 43 : $paymentMethod.hashCode());
        java.lang.Object $tipAmount = this.getTipAmount();
        result = result * PRIME + ($tipAmount == null ? 43 : $tipAmount.hashCode());
        java.lang.Object $notes = this.getNotes();
        result = result * PRIME + ($notes == null ? 43 : $notes.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "CreateOrderRequest(items=" + this.getItems() + ", paymentMethod=" + this.getPaymentMethod() + ", tipAmount=" + this.getTipAmount() + ", notes=" + this.getNotes() + ")";
    }
}
