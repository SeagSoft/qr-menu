package com.qrmenu.platform.menu.entity;

import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_variant")
public class ProductVariant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;


    public static class ProductVariantBuilder {
        private Long id;
        private Product product;
        private String name;
        private BigDecimal price;

        ProductVariantBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ProductVariant.ProductVariantBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductVariant.ProductVariantBuilder product(Product product) {
            this.product = product;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductVariant.ProductVariantBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductVariant.ProductVariantBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductVariant build() {
            return new ProductVariant(this.id, this.product, this.name, this.price);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ProductVariant.ProductVariantBuilder(id=" + this.id + ", product=" + this.product + ", name=" + this.name + ", price=" + this.price + ")";
        }
    }

    public static ProductVariant.ProductVariantBuilder builder() {
        return new ProductVariant.ProductVariantBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Product getProduct() {
        return this.product;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductVariant() {
    }

    public ProductVariant(Long id, Product product, String name, BigDecimal price) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.price = price;
    }
}
