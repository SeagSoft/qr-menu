package com.qrmenu.platform.ordering.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder order;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;
    @Column(name = "selected_variant_name")
    private String selectedVariantName;
    @Column(name = "selected_options_text", columnDefinition = "TEXT")
    private String selectedOptionsText;
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;


    public static class OrderItemBuilder {
        private Long id;
        private CustomerOrder order;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private String selectedVariantName;
        private String selectedOptionsText;
        private String notes;

        OrderItemBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public OrderItem.OrderItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItem.OrderItemBuilder order(CustomerOrder order) {
            this.order = order;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItem.OrderItemBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItem.OrderItemBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItem.OrderItemBuilder unitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItem.OrderItemBuilder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItem.OrderItemBuilder selectedVariantName(String selectedVariantName) {
            this.selectedVariantName = selectedVariantName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItem.OrderItemBuilder selectedOptionsText(String selectedOptionsText) {
            this.selectedOptionsText = selectedOptionsText;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OrderItem.OrderItemBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this.id, this.order, this.productName, this.quantity, this.unitPrice, this.totalPrice, this.selectedVariantName, this.selectedOptionsText, this.notes);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "OrderItem.OrderItemBuilder(id=" + this.id + ", order=" + this.order + ", productName=" + this.productName + ", quantity=" + this.quantity + ", unitPrice=" + this.unitPrice + ", totalPrice=" + this.totalPrice + ", selectedVariantName=" + this.selectedVariantName + ", selectedOptionsText=" + this.selectedOptionsText + ", notes=" + this.notes + ")";
        }
    }

    public static OrderItem.OrderItemBuilder builder() {
        return new OrderItem.OrderItemBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public CustomerOrder getOrder() {
        return this.order;
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

    public void setOrder(CustomerOrder order) {
        this.order = order;
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

    public OrderItem() {
    }

    public OrderItem(Long id, CustomerOrder order, String productName, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice, String selectedVariantName, String selectedOptionsText, String notes) {
        this.id = id;
        this.order = order;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.selectedVariantName = selectedVariantName;
        this.selectedOptionsText = selectedOptionsText;
        this.notes = notes;
    }
}
