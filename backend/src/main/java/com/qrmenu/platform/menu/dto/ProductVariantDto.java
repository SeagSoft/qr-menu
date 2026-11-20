package com.qrmenu.platform.menu.dto;

import java.math.BigDecimal;

public class ProductVariantDto {
    private Long id;
    private String name;
    private BigDecimal price;


    public static class ProductVariantDtoBuilder {
        private Long id;
        private String name;
        private BigDecimal price;

        ProductVariantDtoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ProductVariantDto.ProductVariantDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductVariantDto.ProductVariantDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductVariantDto.ProductVariantDtoBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductVariantDto build() {
            return new ProductVariantDto(this.id, this.name, this.price);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ProductVariantDto.ProductVariantDtoBuilder(id=" + this.id + ", name=" + this.name + ", price=" + this.price + ")";
        }
    }

    public static ProductVariantDto.ProductVariantDtoBuilder builder() {
        return new ProductVariantDto.ProductVariantDtoBuilder();
    }

    public Long getId() {
        return this.id;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductVariantDto)) return false;
        ProductVariantDto other = (ProductVariantDto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        java.lang.Object this$id = this.getId();
        java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        java.lang.Object this$name = this.getName();
        java.lang.Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        java.lang.Object this$price = this.getPrice();
        java.lang.Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof ProductVariantDto;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        java.lang.Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ProductVariantDto(id=" + this.getId() + ", name=" + this.getName() + ", price=" + this.getPrice() + ")";
    }

    public ProductVariantDto() {
    }

    public ProductVariantDto(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
