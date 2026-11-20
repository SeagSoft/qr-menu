package com.qrmenu.platform.menu.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class UpdateProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    private String name;
    private String description;
    @NotNull(message = "Price is required")
    private BigDecimal price;
    @Size(max = 512, message = "Image URL must not exceed 512 characters")
    private String imageUrl;
    private int sortOrder;
    private Integer calories;
    private Integer prepTimeMinutes;
    private List<String> tags;
    @Valid
    private List<CreateProductRequest.VariantRequest> variants;
    @Valid
    private List<CreateProductRequest.OptionGroupRequest> optionGroups;


    public static class UpdateProductRequestBuilder {
        private String name;
        private String description;
        private BigDecimal price;
        private String imageUrl;
        private int sortOrder;
        private Integer calories;
        private Integer prepTimeMinutes;
        private List<String> tags;
        private List<CreateProductRequest.VariantRequest> variants;
        private List<CreateProductRequest.OptionGroupRequest> optionGroups;

        UpdateProductRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public UpdateProductRequest.UpdateProductRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProductRequest.UpdateProductRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProductRequest.UpdateProductRequestBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProductRequest.UpdateProductRequestBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProductRequest.UpdateProductRequestBuilder sortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProductRequest.UpdateProductRequestBuilder calories(Integer calories) {
            this.calories = calories;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProductRequest.UpdateProductRequestBuilder prepTimeMinutes(Integer prepTimeMinutes) {
            this.prepTimeMinutes = prepTimeMinutes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProductRequest.UpdateProductRequestBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProductRequest.UpdateProductRequestBuilder variants(List<CreateProductRequest.VariantRequest> variants) {
            this.variants = variants;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProductRequest.UpdateProductRequestBuilder optionGroups(List<CreateProductRequest.OptionGroupRequest> optionGroups) {
            this.optionGroups = optionGroups;
            return this;
        }

        public UpdateProductRequest build() {
            return new UpdateProductRequest(this.name, this.description, this.price, this.imageUrl, this.sortOrder, this.calories, this.prepTimeMinutes, this.tags, this.variants, this.optionGroups);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "UpdateProductRequest.UpdateProductRequestBuilder(name=" + this.name + ", description=" + this.description + ", price=" + this.price + ", imageUrl=" + this.imageUrl + ", sortOrder=" + this.sortOrder + ", calories=" + this.calories + ", prepTimeMinutes=" + this.prepTimeMinutes + ", tags=" + this.tags + ", variants=" + this.variants + ", optionGroups=" + this.optionGroups + ")";
        }
    }

    public static UpdateProductRequest.UpdateProductRequestBuilder builder() {
        return new UpdateProductRequest.UpdateProductRequestBuilder();
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

    public Integer getCalories() {
        return this.calories;
    }

    public Integer getPrepTimeMinutes() {
        return this.prepTimeMinutes;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public List<CreateProductRequest.VariantRequest> getVariants() {
        return this.variants;
    }

    public List<CreateProductRequest.OptionGroupRequest> getOptionGroups() {
        return this.optionGroups;
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

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setPrepTimeMinutes(Integer prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setVariants(List<CreateProductRequest.VariantRequest> variants) {
        this.variants = variants;
    }

    public void setOptionGroups(List<CreateProductRequest.OptionGroupRequest> optionGroups) {
        this.optionGroups = optionGroups;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof UpdateProductRequest)) return false;
        UpdateProductRequest other = (UpdateProductRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getSortOrder() != other.getSortOrder()) return false;
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
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof UpdateProductRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getSortOrder();
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
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "UpdateProductRequest(name=" + this.getName() + ", description=" + this.getDescription() + ", price=" + this.getPrice() + ", imageUrl=" + this.getImageUrl() + ", sortOrder=" + this.getSortOrder() + ", calories=" + this.getCalories() + ", prepTimeMinutes=" + this.getPrepTimeMinutes() + ", tags=" + this.getTags() + ", variants=" + this.getVariants() + ", optionGroups=" + this.getOptionGroups() + ")";
    }

    public UpdateProductRequest() {
    }

    public UpdateProductRequest(String name, String description, BigDecimal price, String imageUrl, int sortOrder, Integer calories, Integer prepTimeMinutes, List<String> tags, List<CreateProductRequest.VariantRequest> variants, List<CreateProductRequest.OptionGroupRequest> optionGroups) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.calories = calories;
        this.prepTimeMinutes = prepTimeMinutes;
        this.tags = tags;
        this.variants = variants;
        this.optionGroups = optionGroups;
    }
}
