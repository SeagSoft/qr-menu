package com.qrmenu.platform.menu.entity;

import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_option")
public class ProductOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private OptionGroup optionGroup;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price_modifier", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceModifier;

    private static BigDecimal $default$priceModifier() {
        return BigDecimal.ZERO;
    }


    public static class ProductOptionBuilder {
        private Long id;
        private OptionGroup optionGroup;
        private String name;
        private boolean priceModifier$set;
        private BigDecimal priceModifier$value;

        ProductOptionBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ProductOption.ProductOptionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductOption.ProductOptionBuilder optionGroup(OptionGroup optionGroup) {
            this.optionGroup = optionGroup;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductOption.ProductOptionBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductOption.ProductOptionBuilder priceModifier(BigDecimal priceModifier) {
            this.priceModifier$value = priceModifier;
            priceModifier$set = true;
            return this;
        }

        public ProductOption build() {
            BigDecimal priceModifier$value = this.priceModifier$value;
            if (!this.priceModifier$set) priceModifier$value = ProductOption.$default$priceModifier();
            return new ProductOption(this.id, this.optionGroup, this.name, priceModifier$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ProductOption.ProductOptionBuilder(id=" + this.id + ", optionGroup=" + this.optionGroup + ", name=" + this.name + ", priceModifier$value=" + this.priceModifier$value + ")";
        }
    }

    public static ProductOption.ProductOptionBuilder builder() {
        return new ProductOption.ProductOptionBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public OptionGroup getOptionGroup() {
        return this.optionGroup;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPriceModifier() {
        return this.priceModifier;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOptionGroup(OptionGroup optionGroup) {
        this.optionGroup = optionGroup;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriceModifier(BigDecimal priceModifier) {
        this.priceModifier = priceModifier;
    }

    public ProductOption() {
        this.priceModifier = ProductOption.$default$priceModifier();
    }

    public ProductOption(Long id, OptionGroup optionGroup, String name, BigDecimal priceModifier) {
        this.id = id;
        this.optionGroup = optionGroup;
        this.name = name;
        this.priceModifier = priceModifier;
    }
}
