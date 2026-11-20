package com.qrmenu.platform.menu.dto;

import java.math.BigDecimal;

public class ProductOptionDto {
    private Long id;
    private String name;
    private BigDecimal priceModifier;


    public static class ProductOptionDtoBuilder {
        private Long id;
        private String name;
        private BigDecimal priceModifier;

        ProductOptionDtoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ProductOptionDto.ProductOptionDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductOptionDto.ProductOptionDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductOptionDto.ProductOptionDtoBuilder priceModifier(BigDecimal priceModifier) {
            this.priceModifier = priceModifier;
            return this;
        }

        public ProductOptionDto build() {
            return new ProductOptionDto(this.id, this.name, this.priceModifier);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ProductOptionDto.ProductOptionDtoBuilder(id=" + this.id + ", name=" + this.name + ", priceModifier=" + this.priceModifier + ")";
        }
    }

    public static ProductOptionDto.ProductOptionDtoBuilder builder() {
        return new ProductOptionDto.ProductOptionDtoBuilder();
    }

    public Long getId() {
        return this.id;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPriceModifier(BigDecimal priceModifier) {
        this.priceModifier = priceModifier;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductOptionDto)) return false;
        ProductOptionDto other = (ProductOptionDto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$id = this.getId();
        java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        java.lang.Object this$name = this.getName();
        java.lang.Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        java.lang.Object this$priceModifier = this.getPriceModifier();
        java.lang.Object other$priceModifier = other.getPriceModifier();
        if (this$priceModifier == null ? other$priceModifier != null : !this$priceModifier.equals(other$priceModifier)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof ProductOptionDto;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        java.lang.Object $priceModifier = this.getPriceModifier();
        result = result * PRIME + ($priceModifier == null ? 43 : $priceModifier.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ProductOptionDto(id=" + this.getId() + ", name=" + this.getName() + ", priceModifier=" + this.getPriceModifier() + ")";
    }

    public ProductOptionDto() {
    }

    public ProductOptionDto(Long id, String name, BigDecimal priceModifier) {
        this.id = id;
        this.name = name;
        this.priceModifier = priceModifier;
    }
}
