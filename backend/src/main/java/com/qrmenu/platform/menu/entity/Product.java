package com.qrmenu.platform.menu.entity;

import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private MenuCategory category;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "image_url", length = 512)
    private String imageUrl;
    @Column(name = "sort_order", nullable = false)
    private int sortOrder;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "calories")
    private Integer calories;
    @Column(name = "prep_time_minutes")
    private Integer prepTimeMinutes;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionGroup> optionGroups;

    private static int $default$sortOrder() {
        return 0;
    }

    private static boolean $default$isActive() {
        return true;
    }

    private static List<String> $default$tags() {
        return new ArrayList<>();
    }

    private static List<ProductVariant> $default$variants() {
        return new ArrayList<>();
    }

    private static List<OptionGroup> $default$optionGroups() {
        return new ArrayList<>();
    }


    public static class ProductBuilder {
        private Long id;
        private MenuCategory category;
        private String name;
        private String description;
        private BigDecimal price;
        private String imageUrl;
        private boolean sortOrder$set;
        private int sortOrder$value;
        private boolean isActive$set;
        private boolean isActive$value;
        private Integer calories;
        private Integer prepTimeMinutes;
        private boolean tags$set;
        private List<String> tags$value;
        private boolean variants$set;
        private List<ProductVariant> variants$value;
        private boolean optionGroups$set;
        private List<OptionGroup> optionGroups$value;

        ProductBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder category(MenuCategory category) {
            this.category = category;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder sortOrder(int sortOrder) {
            this.sortOrder$value = sortOrder;
            sortOrder$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder isActive(boolean isActive) {
            this.isActive$value = isActive;
            isActive$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder calories(Integer calories) {
            this.calories = calories;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder prepTimeMinutes(Integer prepTimeMinutes) {
            this.prepTimeMinutes = prepTimeMinutes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder tags(List<String> tags) {
            this.tags$value = tags;
            tags$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder variants(List<ProductVariant> variants) {
            this.variants$value = variants;
            variants$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Product.ProductBuilder optionGroups(List<OptionGroup> optionGroups) {
            this.optionGroups$value = optionGroups;
            optionGroups$set = true;
            return this;
        }

        public Product build() {
            int sortOrder$value = this.sortOrder$value;
            if (!this.sortOrder$set) sortOrder$value = Product.$default$sortOrder();
            boolean isActive$value = this.isActive$value;
            if (!this.isActive$set) isActive$value = Product.$default$isActive();
            List<String> tags$value = this.tags$value;
            if (!this.tags$set) tags$value = Product.$default$tags();
            List<ProductVariant> variants$value = this.variants$value;
            if (!this.variants$set) variants$value = Product.$default$variants();
            List<OptionGroup> optionGroups$value = this.optionGroups$value;
            if (!this.optionGroups$set) optionGroups$value = Product.$default$optionGroups();
            return new Product(this.id, this.category, this.name, this.description, this.price, this.imageUrl, sortOrder$value, isActive$value, this.calories, this.prepTimeMinutes, tags$value, variants$value, optionGroups$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Product.ProductBuilder(id=" + this.id + ", category=" + this.category + ", name=" + this.name + ", description=" + this.description + ", price=" + this.price + ", imageUrl=" + this.imageUrl + ", sortOrder$value=" + this.sortOrder$value + ", isActive$value=" + this.isActive$value + ", calories=" + this.calories + ", prepTimeMinutes=" + this.prepTimeMinutes + ", tags$value=" + this.tags$value + ", variants$value=" + this.variants$value + ", optionGroups$value=" + this.optionGroups$value + ")";
        }
    }

    public static Product.ProductBuilder builder() {
        return new Product.ProductBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public MenuCategory getCategory() {
        return this.category;
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

    public List<ProductVariant> getVariants() {
        return this.variants;
    }

    public List<OptionGroup> getOptionGroups() {
        return this.optionGroups;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(MenuCategory category) {
        this.category = category;
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

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }

    public void setOptionGroups(List<OptionGroup> optionGroups) {
        this.optionGroups = optionGroups;
    }

    public Product() {
        this.sortOrder = Product.$default$sortOrder();
        this.isActive = Product.$default$isActive();
        this.tags = Product.$default$tags();
        this.variants = Product.$default$variants();
        this.optionGroups = Product.$default$optionGroups();
    }

    public Product(Long id, MenuCategory category, String name, String description, BigDecimal price, String imageUrl, int sortOrder, boolean isActive, Integer calories, Integer prepTimeMinutes, List<String> tags, List<ProductVariant> variants, List<OptionGroup> optionGroups) {
        this.id = id;
        this.category = category;
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
    }
}
