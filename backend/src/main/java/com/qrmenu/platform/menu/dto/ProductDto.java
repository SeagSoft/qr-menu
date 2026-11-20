package com.qrmenu.platform.menu.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ProductDto {
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private int sortOrder;
    private boolean isActive;
    private Integer calories;
    private Integer prepTimeMinutes;
    private List<String> tags;
    private List<ProductVariantDto> variants;
    private List<OptionGroupDto> optionGroups;
    private Instant createdAt;
    private Instant updatedAt;


    public static class ProductDtoBuilder {
        private Long id;
        private Long categoryId;
        private String name;
        private String description;
        private BigDecimal price;
        private String imageUrl;
        private int sortOrder;
        private boolean isActive;
        private Integer calories;
        private Integer prepTimeMinutes;
        private List<String> tags;
        private List<ProductVariantDto> variants;
        private List<OptionGroupDto> optionGroups;
        private Instant createdAt;
        private Instant updatedAt;

        ProductDtoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder sortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder calories(Integer calories) {
            this.calories = calories;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder prepTimeMinutes(Integer prepTimeMinutes) {
            this.prepTimeMinutes = prepTimeMinutes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder variants(List<ProductVariantDto> variants) {
            this.variants = variants;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder optionGroups(List<OptionGroupDto> optionGroups) {
            this.optionGroups = optionGroups;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProductDto.ProductDtoBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(this.id, this.categoryId, this.name, this.description, this.price, this.imageUrl, this.sortOrder, this.isActive, this.calories, this.prepTimeMinutes, this.tags, this.variants, this.optionGroups, this.createdAt, this.updatedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ProductDto.ProductDtoBuilder(id=" + this.id + ", categoryId=" + this.categoryId + ", name=" + this.name + ", description=" + this.description + ", price=" + this.price + ", imageUrl=" + this.imageUrl + ", sortOrder=" + this.sortOrder + ", isActive=" + this.isActive + ", calories=" + this.calories + ", prepTimeMinutes=" + this.prepTimeMinutes + ", tags=" + this.tags + ", variants=" + this.variants + ", optionGroups=" + this.optionGroups + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }

    public static ProductDto.ProductDtoBuilder builder() {
        return new ProductDto.ProductDtoBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public Integer getCalories() {
        return this.calories;
    }

    public Integer getPrepTimeMinutes() {
        return this.prepTimeMinutes;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public List<ProductVariantDto> getVariants() {
        return this.variants;
    }

    public List<OptionGroupDto> getOptionGroups() {
        return this.optionGroups;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setPrepTimeMinutes(Integer prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setVariants(List<ProductVariantDto> variants) {
        this.variants = variants;
    }

    public void setOptionGroups(List<OptionGroupDto> optionGroups) {
        this.optionGroups = optionGroups;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductDto)) return false;
        ProductDto other = (ProductDto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getSortOrder() != other.getSortOrder()) return false;
        if (this.isActive() != other.isActive()) return false;
        java.lang.Object this$id = this.getId();
        java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        java.lang.Object this$categoryId = this.getCategoryId();
        java.lang.Object other$categoryId = other.getCategoryId();
        if (this$categoryId == null ? other$categoryId != null : !this$categoryId.equals(other$categoryId)) return false;
        java.lang.Object this$calories = this.getCalories();
        java.lang.Object other$calories = other.getCalories();
        if (this$calories == null ? other$calories != null : !this$calories.equals(other$calories)) return false;
        java.lang.Object this$prepTimeMinutes = this.getPrepTimeMinutes();
        java.lang.Object other$prepTimeMinutes = other.getPrepTimeMinutes();
        if (this$prepTimeMinutes == null ? other$prepTimeMinutes != null : !this$prepTimeMinutes.equals(other$prepTimeMinutes)) return false;
        java.lang.Object this$name = this.getName();
        java.lang.Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        java.lang.Object this$description = this.getDescription();
        java.lang.Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        java.lang.Object this$price = this.getPrice();
        java.lang.Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        java.lang.Object this$imageUrl = this.getImageUrl();
        java.lang.Object other$imageUrl = other.getImageUrl();
        if (this$imageUrl == null ? other$imageUrl != null : !this$imageUrl.equals(other$imageUrl)) return false;
        java.lang.Object this$tags = this.getTags();
        java.lang.Object other$tags = other.getTags();
        if (this$tags == null ? other$tags != null : !this$tags.equals(other$tags)) return false;
        java.lang.Object this$variants = this.getVariants();
        java.lang.Object other$variants = other.getVariants();
        if (this$variants == null ? other$variants != null : !this$variants.equals(other$variants)) return false;
        java.lang.Object this$optionGroups = this.getOptionGroups();
        java.lang.Object other$optionGroups = other.getOptionGroups();
        if (this$optionGroups == null ? other$optionGroups != null : !this$optionGroups.equals(other$optionGroups)) return false;
        java.lang.Object this$createdAt = this.getCreatedAt();
        java.lang.Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        java.lang.Object this$updatedAt = this.getUpdatedAt();
        java.lang.Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof ProductDto;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getSortOrder();
        result = result * PRIME + (this.isActive() ? 79 : 97);
        java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        java.lang.Object $categoryId = this.getCategoryId();
        result = result * PRIME + ($categoryId == null ? 43 : $categoryId.hashCode());
        java.lang.Object $calories = this.getCalories();
        result = result * PRIME + ($calories == null ? 43 : $calories.hashCode());
        java.lang.Object $prepTimeMinutes = this.getPrepTimeMinutes();
        result = result * PRIME + ($prepTimeMinutes == null ? 43 : $prepTimeMinutes.hashCode());
        java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        java.lang.Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        java.lang.Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        java.lang.Object $imageUrl = this.getImageUrl();
        result = result * PRIME + ($imageUrl == null ? 43 : $imageUrl.hashCode());
        java.lang.Object $tags = this.getTags();
        result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
        java.lang.Object $variants = this.getVariants();
        result = result * PRIME + ($variants == null ? 43 : $variants.hashCode());
        java.lang.Object $optionGroups = this.getOptionGroups();
        result = result * PRIME + ($optionGroups == null ? 43 : $optionGroups.hashCode());
        java.lang.Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        java.lang.Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ProductDto(id=" + this.getId() + ", categoryId=" + this.getCategoryId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", price=" + this.getPrice() + ", imageUrl=" + this.getImageUrl() + ", sortOrder=" + this.getSortOrder() + ", isActive=" + this.isActive() + ", calories=" + this.getCalories() + ", prepTimeMinutes=" + this.getPrepTimeMinutes() + ", tags=" + this.getTags() + ", variants=" + this.getVariants() + ", optionGroups=" + this.getOptionGroups() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public ProductDto() {
    }

    public ProductDto(Long id, Long categoryId, String name, String description, BigDecimal price, String imageUrl, int sortOrder, boolean isActive, Integer calories, Integer prepTimeMinutes, List<String> tags, List<ProductVariantDto> variants, List<OptionGroupDto> optionGroups, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.calories = calories;
        this.prepTimeMinutes = prepTimeMinutes;
        this.tags = tags;
        this.variants = variants;
        this.optionGroups = optionGroups;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
