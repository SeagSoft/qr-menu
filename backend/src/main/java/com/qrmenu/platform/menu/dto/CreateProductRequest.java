package com.qrmenu.platform.menu.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class CreateProductRequest {
    @NotNull(message = "Category ID is required")
    private Long categoryId;
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
    private List<VariantRequest> variants;
    @Valid
    private List<OptionGroupRequest> optionGroups;


    public static class VariantRequest {
        @NotBlank(message = "Variant name is required")
        @Size(max = 100, message = "Variant name must not exceed 100 characters")
        private String name;
        @NotNull(message = "Variant price is required")
        private BigDecimal price;


        public static class VariantRequestBuilder {
            private String name;
            private BigDecimal price;

            VariantRequestBuilder() {
            }

            /**
             * @return {@code this}.
             */
            public CreateProductRequest.VariantRequest.VariantRequestBuilder name(String name) {
                this.name = name;
                return this;
            }

            /**
             * @return {@code this}.
             */
            public CreateProductRequest.VariantRequest.VariantRequestBuilder price(BigDecimal price) {
                this.price = price;
                return this;
            }

            public CreateProductRequest.VariantRequest build() {
                return new CreateProductRequest.VariantRequest(this.name, this.price);
            }

            @java.lang.Override
            public java.lang.String toString() {
                return "CreateProductRequest.VariantRequest.VariantRequestBuilder(name=" + this.name + ", price=" + this.price + ")";
            }
        }

        public static CreateProductRequest.VariantRequest.VariantRequestBuilder builder() {
            return new CreateProductRequest.VariantRequest.VariantRequestBuilder();
        }

        public String getName() {
            return this.name;
        }

        public BigDecimal getPrice() {
            return this.price;
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
            if (!(o instanceof CreateProductRequest.VariantRequest)) return false;
            CreateProductRequest.VariantRequest other = (CreateProductRequest.VariantRequest) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            java.lang.Object this$name = this.getName();
            java.lang.Object other$name = other.getName();
            if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
            java.lang.Object this$price = this.getPrice();
            java.lang.Object other$price = other.getPrice();
            if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
            return true;
        }

        protected boolean canEqual(java.lang.Object other) {
            return other instanceof CreateProductRequest.VariantRequest;
        }

        @java.lang.Override
        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            java.lang.Object $name = this.getName();
            result = result * PRIME + ($name == null ? 43 : $name.hashCode());
            java.lang.Object $price = this.getPrice();
            result = result * PRIME + ($price == null ? 43 : $price.hashCode());
            return result;
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "CreateProductRequest.VariantRequest(name=" + this.getName() + ", price=" + this.getPrice() + ")";
        }

        public VariantRequest() {
        }

        public VariantRequest(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }
    }


    public static class OptionGroupRequest {
        @NotBlank(message = "Option group name is required")
        @Size(max = 100, message = "Option group name must not exceed 100 characters")
        private String name;
        private int minSelect;
        private int maxSelect;
        @Valid
        private List<OptionRequest> options;


        public static class OptionGroupRequestBuilder {
            private String name;
            private int minSelect;
            private int maxSelect;
            private List<OptionRequest> options;

            OptionGroupRequestBuilder() {
            }

            /**
             * @return {@code this}.
             */
            public CreateProductRequest.OptionGroupRequest.OptionGroupRequestBuilder name(String name) {
                this.name = name;
                return this;
            }

            /**
             * @return {@code this}.
             */
            public CreateProductRequest.OptionGroupRequest.OptionGroupRequestBuilder minSelect(int minSelect) {
                this.minSelect = minSelect;
                return this;
            }

            /**
             * @return {@code this}.
             */
            public CreateProductRequest.OptionGroupRequest.OptionGroupRequestBuilder maxSelect(int maxSelect) {
                this.maxSelect = maxSelect;
                return this;
            }

            /**
             * @return {@code this}.
             */
            public CreateProductRequest.OptionGroupRequest.OptionGroupRequestBuilder options(List<OptionRequest> options) {
                this.options = options;
                return this;
            }

            public CreateProductRequest.OptionGroupRequest build() {
                return new CreateProductRequest.OptionGroupRequest(this.name, this.minSelect, this.maxSelect, this.options);
            }

            @java.lang.Override
            public java.lang.String toString() {
                return "CreateProductRequest.OptionGroupRequest.OptionGroupRequestBuilder(name=" + this.name + ", minSelect=" + this.minSelect + ", maxSelect=" + this.maxSelect + ", options=" + this.options + ")";
            }
        }

        public static CreateProductRequest.OptionGroupRequest.OptionGroupRequestBuilder builder() {
            return new CreateProductRequest.OptionGroupRequest.OptionGroupRequestBuilder();
        }

        public String getName() {
            return this.name;
        }

        public int getMinSelect() {
            return this.minSelect;
        }

        public int getMaxSelect() {
            return this.maxSelect;
        }

        public List<OptionRequest> getOptions() {
            return this.options;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMinSelect(int minSelect) {
            this.minSelect = minSelect;
        }

        public void setMaxSelect(int maxSelect) {
            this.maxSelect = maxSelect;
        }

        public void setOptions(List<OptionRequest> options) {
            this.options = options;
        }

        @java.lang.Override
        public boolean equals(java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof CreateProductRequest.OptionGroupRequest)) return false;
            CreateProductRequest.OptionGroupRequest other = (CreateProductRequest.OptionGroupRequest) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            if (this.getMinSelect() != other.getMinSelect()) return false;
            if (this.getMaxSelect() != other.getMaxSelect()) return false;
            java.lang.Object this$name = this.getName();
            java.lang.Object other$name = other.getName();
            if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
            java.lang.Object this$options = this.getOptions();
            java.lang.Object other$options = other.getOptions();
            if (this$options == null ? other$options != null : !this$options.equals(other$options)) return false;
            return true;
        }

        protected boolean canEqual(java.lang.Object other) {
            return other instanceof CreateProductRequest.OptionGroupRequest;
        }

        @java.lang.Override
        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            result = result * PRIME + this.getMinSelect();
            result = result * PRIME + this.getMaxSelect();
            java.lang.Object $name = this.getName();
            result = result * PRIME + ($name == null ? 43 : $name.hashCode());
            java.lang.Object $options = this.getOptions();
            result = result * PRIME + ($options == null ? 43 : $options.hashCode());
            return result;
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "CreateProductRequest.OptionGroupRequest(name=" + this.getName() + ", minSelect=" + this.getMinSelect() + ", maxSelect=" + this.getMaxSelect() + ", options=" + this.getOptions() + ")";
        }

        public OptionGroupRequest() {
        }

        public OptionGroupRequest(String name, int minSelect, int maxSelect, List<OptionRequest> options) {
            this.name = name;
            this.minSelect = minSelect;
            this.maxSelect = maxSelect;
            this.options = options;
        }
    }


    public static class OptionRequest {
        @NotBlank(message = "Option name is required")
        @Size(max = 100, message = "Option name must not exceed 100 characters")
        private String name;
        private BigDecimal priceModifier;


        public static class OptionRequestBuilder {
            private String name;
            private BigDecimal priceModifier;

            OptionRequestBuilder() {
            }

            /**
             * @return {@code this}.
             */
            public CreateProductRequest.OptionRequest.OptionRequestBuilder name(String name) {
                this.name = name;
                return this;
            }

            /**
             * @return {@code this}.
             */
            public CreateProductRequest.OptionRequest.OptionRequestBuilder priceModifier(BigDecimal priceModifier) {
                this.priceModifier = priceModifier;
                return this;
            }

            public CreateProductRequest.OptionRequest build() {
                return new CreateProductRequest.OptionRequest(this.name, this.priceModifier);
            }

            @java.lang.Override
            public java.lang.String toString() {
                return "CreateProductRequest.OptionRequest.OptionRequestBuilder(name=" + this.name + ", priceModifier=" + this.priceModifier + ")";
            }
        }

        public static CreateProductRequest.OptionRequest.OptionRequestBuilder builder() {
            return new CreateProductRequest.OptionRequest.OptionRequestBuilder();
        }

        public String getName() {
            return this.name;
        }

        public BigDecimal getPriceModifier() {
            return this.priceModifier;
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
            if (!(o instanceof CreateProductRequest.OptionRequest)) return false;
            CreateProductRequest.OptionRequest other = (CreateProductRequest.OptionRequest) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            java.lang.Object this$name = this.getName();
            java.lang.Object other$name = other.getName();
            if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
            java.lang.Object this$priceModifier = this.getPriceModifier();
            java.lang.Object other$priceModifier = other.getPriceModifier();
            if (this$priceModifier == null ? other$priceModifier != null : !this$priceModifier.equals(other$priceModifier)) return false;
            return true;
        }

        protected boolean canEqual(java.lang.Object other) {
            return other instanceof CreateProductRequest.OptionRequest;
        }

        @java.lang.Override
        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            java.lang.Object $name = this.getName();
            result = result * PRIME + ($name == null ? 43 : $name.hashCode());
            java.lang.Object $priceModifier = this.getPriceModifier();
            result = result * PRIME + ($priceModifier == null ? 43 : $priceModifier.hashCode());
            return result;
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "CreateProductRequest.OptionRequest(name=" + this.getName() + ", priceModifier=" + this.getPriceModifier() + ")";
        }

        public OptionRequest() {
        }

        public OptionRequest(String name, BigDecimal priceModifier) {
            this.name = name;
            this.priceModifier = priceModifier;
        }
    }


    public static class CreateProductRequestBuilder {
        private Long categoryId;
        private String name;
        private String description;
        private BigDecimal price;
        private String imageUrl;
        private int sortOrder;
        private Integer calories;
        private Integer prepTimeMinutes;
        private List<String> tags;
        private List<VariantRequest> variants;
        private List<OptionGroupRequest> optionGroups;

        CreateProductRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder sortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder calories(Integer calories) {
            this.calories = calories;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder prepTimeMinutes(Integer prepTimeMinutes) {
            this.prepTimeMinutes = prepTimeMinutes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder variants(List<VariantRequest> variants) {
            this.variants = variants;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateProductRequest.CreateProductRequestBuilder optionGroups(List<OptionGroupRequest> optionGroups) {
            this.optionGroups = optionGroups;
            return this;
        }

        public CreateProductRequest build() {
            return new CreateProductRequest(this.categoryId, this.name, this.description, this.price, this.imageUrl, this.sortOrder, this.calories, this.prepTimeMinutes, this.tags, this.variants, this.optionGroups);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "CreateProductRequest.CreateProductRequestBuilder(categoryId=" + this.categoryId + ", name=" + this.name + ", description=" + this.description + ", price=" + this.price + ", imageUrl=" + this.imageUrl + ", sortOrder=" + this.sortOrder + ", calories=" + this.calories + ", prepTimeMinutes=" + this.prepTimeMinutes + ", tags=" + this.tags + ", variants=" + this.variants + ", optionGroups=" + this.optionGroups + ")";
        }
    }

    public static CreateProductRequest.CreateProductRequestBuilder builder() {
        return new CreateProductRequest.CreateProductRequestBuilder();
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

    public Integer getCalories() {
        return this.calories;
    }

    public Integer getPrepTimeMinutes() {
        return this.prepTimeMinutes;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public List<VariantRequest> getVariants() {
        return this.variants;
    }

    public List<OptionGroupRequest> getOptionGroups() {
        return this.optionGroups;
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

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setPrepTimeMinutes(Integer prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setVariants(List<VariantRequest> variants) {
        this.variants = variants;
    }

    public void setOptionGroups(List<OptionGroupRequest> optionGroups) {
        this.optionGroups = optionGroups;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateProductRequest)) return false;
        CreateProductRequest other = (CreateProductRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getSortOrder() != other.getSortOrder()) return false;
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
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof CreateProductRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getSortOrder();
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
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "CreateProductRequest(categoryId=" + this.getCategoryId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", price=" + this.getPrice() + ", imageUrl=" + this.getImageUrl() + ", sortOrder=" + this.getSortOrder() + ", calories=" + this.getCalories() + ", prepTimeMinutes=" + this.getPrepTimeMinutes() + ", tags=" + this.getTags() + ", variants=" + this.getVariants() + ", optionGroups=" + this.getOptionGroups() + ")";
    }

    public CreateProductRequest() {
    }

    public CreateProductRequest(Long categoryId, String name, String description, BigDecimal price, String imageUrl, int sortOrder, Integer calories, Integer prepTimeMinutes, List<String> tags, List<VariantRequest> variants, List<OptionGroupRequest> optionGroups) {
        this.categoryId = categoryId;
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
