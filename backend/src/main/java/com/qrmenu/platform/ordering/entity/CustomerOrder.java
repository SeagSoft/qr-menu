package com.qrmenu.platform.ordering.entity;

import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.table.entity.RestaurantTable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTable table;
    @Column(name = "order_number", nullable = false, unique = true, length = 32)
    private String orderNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private OrderStatus status;
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    @Column(name = "payment_method", nullable = false, length = 32)
    private String paymentMethod;
    @Column(name = "tip_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal tipAmount;
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
        if (this.paymentMethod == null || this.paymentMethod.isBlank()) {
            this.paymentMethod = "CARD";
        }
        if (this.tipAmount == null) {
            this.tipAmount = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    private static String $default$paymentMethod() {
        return "CARD";
    }

    private static BigDecimal $default$tipAmount() {
        return BigDecimal.ZERO;
    }

    private static List<OrderItem> $default$items() {
        return new ArrayList<>();
    }


    public static class CustomerOrderBuilder {
        private Long id;
        private Branch branch;
        private RestaurantTable table;
        private String orderNumber;
        private OrderStatus status;
        private BigDecimal totalAmount;
        private boolean paymentMethod$set;
        private String paymentMethod$value;
        private boolean tipAmount$set;
        private BigDecimal tipAmount$value;
        private String notes;
        private boolean items$set;
        private List<OrderItem> items$value;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        CustomerOrderBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder branch(Branch branch) {
            this.branch = branch;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder table(RestaurantTable table) {
            this.table = table;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder paymentMethod(String paymentMethod) {
            this.paymentMethod$value = paymentMethod;
            paymentMethod$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder tipAmount(BigDecimal tipAmount) {
            this.tipAmount$value = tipAmount;
            tipAmount$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder items(List<OrderItem> items) {
            this.items$value = items;
            items$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CustomerOrder.CustomerOrderBuilder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public CustomerOrder build() {
            String paymentMethod$value = this.paymentMethod$value;
            if (!this.paymentMethod$set) paymentMethod$value = CustomerOrder.$default$paymentMethod();
            BigDecimal tipAmount$value = this.tipAmount$value;
            if (!this.tipAmount$set) tipAmount$value = CustomerOrder.$default$tipAmount();
            List<OrderItem> items$value = this.items$value;
            if (!this.items$set) items$value = CustomerOrder.$default$items();
            return new CustomerOrder(this.id, this.branch, this.table, this.orderNumber, this.status, this.totalAmount, paymentMethod$value, tipAmount$value, this.notes, items$value, this.createdAt, this.updatedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "CustomerOrder.CustomerOrderBuilder(id=" + this.id + ", branch=" + this.branch + ", table=" + this.table + ", orderNumber=" + this.orderNumber + ", status=" + this.status + ", totalAmount=" + this.totalAmount + ", paymentMethod$value=" + this.paymentMethod$value + ", tipAmount$value=" + this.tipAmount$value + ", notes=" + this.notes + ", items$value=" + this.items$value + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }

    public static CustomerOrder.CustomerOrderBuilder builder() {
        return new CustomerOrder.CustomerOrderBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public RestaurantTable getTable() {
        return this.table;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
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

    public List<OrderItem> getItems() {
        return this.items;
    }

    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CustomerOrder() {
        this.paymentMethod = CustomerOrder.$default$paymentMethod();
        this.tipAmount = CustomerOrder.$default$tipAmount();
        this.items = CustomerOrder.$default$items();
    }

    public CustomerOrder(Long id, Branch branch, RestaurantTable table, String orderNumber, OrderStatus status, BigDecimal totalAmount, String paymentMethod, BigDecimal tipAmount, String notes, List<OrderItem> items, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.branch = branch;
        this.table = table;
        this.orderNumber = orderNumber;
        this.status = status;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.tipAmount = tipAmount;
        this.notes = notes;
        this.items = items;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
