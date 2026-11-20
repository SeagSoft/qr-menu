package com.qrmenu.platform.menu.entity;

import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_category")
public class MenuCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "sort_order", nullable = false)
    private int sortOrder;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<Product> products;

    private static int $default$sortOrder() {
        return 0;
    }

    private static boolean $default$isActive() {
        return true;
    }

    private static List<Product> $default$products() {
        return new ArrayList<>();
    }


    public static class MenuCategoryBuilder {
        private Long id;
        private Business business;
        private String name;
        private String description;
        private boolean sortOrder$set;
        private int sortOrder$value;
        private boolean isActive$set;
        private boolean isActive$value;
        private boolean products$set;
        private List<Product> products$value;

        MenuCategoryBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public MenuCategory.MenuCategoryBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategory.MenuCategoryBuilder business(Business business) {
            this.business = business;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategory.MenuCategoryBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategory.MenuCategoryBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategory.MenuCategoryBuilder sortOrder(int sortOrder) {
            this.sortOrder$value = sortOrder;
            sortOrder$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategory.MenuCategoryBuilder isActive(boolean isActive) {
            this.isActive$value = isActive;
            isActive$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategory.MenuCategoryBuilder products(List<Product> products) {
            this.products$value = products;
            products$set = true;
            return this;
        }

        public MenuCategory build() {
            int sortOrder$value = this.sortOrder$value;
            if (!this.sortOrder$set) sortOrder$value = MenuCategory.$default$sortOrder();
            boolean isActive$value = this.isActive$value;
            if (!this.isActive$set) isActive$value = MenuCategory.$default$isActive();
            List<Product> products$value = this.products$value;
            if (!this.products$set) products$value = MenuCategory.$default$products();
            return new MenuCategory(this.id, this.business, this.name, this.description, sortOrder$value, isActive$value, products$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "MenuCategory.MenuCategoryBuilder(id=" + this.id + ", business=" + this.business + ", name=" + this.name + ", description=" + this.description + ", sortOrder$value=" + this.sortOrder$value + ", isActive$value=" + this.isActive$value + ", products$value=" + this.products$value + ")";
        }
    }

    public static MenuCategory.MenuCategoryBuilder builder() {
        return new MenuCategory.MenuCategoryBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Business getBusiness() {
        return this.business;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public MenuCategory() {
        this.sortOrder = MenuCategory.$default$sortOrder();
        this.isActive = MenuCategory.$default$isActive();
        this.products = MenuCategory.$default$products();
    }

    public MenuCategory(Long id, Business business, String name, String description, int sortOrder, boolean isActive, List<Product> products) {
        this.id = id;
        this.business = business;
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.products = products;
    }
}
