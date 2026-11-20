package com.qrmenu.platform.menu.entity;

import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "option_group")
public class OptionGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "min_select", nullable = false)
    private int minSelect;
    @Column(name = "max_select", nullable = false)
    private int maxSelect;
    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options;

    private static int $default$minSelect() {
        return 0;
    }

    private static int $default$maxSelect() {
        return 1;
    }

    private static List<ProductOption> $default$options() {
        return new ArrayList<>();
    }


    public static class OptionGroupBuilder {
        private Long id;
        private Product product;
        private String name;
        private boolean minSelect$set;
        private int minSelect$value;
        private boolean maxSelect$set;
        private int maxSelect$value;
        private boolean options$set;
        private List<ProductOption> options$value;

        OptionGroupBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public OptionGroup.OptionGroupBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OptionGroup.OptionGroupBuilder product(Product product) {
            this.product = product;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OptionGroup.OptionGroupBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OptionGroup.OptionGroupBuilder minSelect(int minSelect) {
            this.minSelect$value = minSelect;
            minSelect$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OptionGroup.OptionGroupBuilder maxSelect(int maxSelect) {
            this.maxSelect$value = maxSelect;
            maxSelect$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OptionGroup.OptionGroupBuilder options(List<ProductOption> options) {
            this.options$value = options;
            options$set = true;
            return this;
        }

        public OptionGroup build() {
            int minSelect$value = this.minSelect$value;
            if (!this.minSelect$set) minSelect$value = OptionGroup.$default$minSelect();
            int maxSelect$value = this.maxSelect$value;
            if (!this.maxSelect$set) maxSelect$value = OptionGroup.$default$maxSelect();
            List<ProductOption> options$value = this.options$value;
            if (!this.options$set) options$value = OptionGroup.$default$options();
            return new OptionGroup(this.id, this.product, this.name, minSelect$value, maxSelect$value, options$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "OptionGroup.OptionGroupBuilder(id=" + this.id + ", product=" + this.product + ", name=" + this.name + ", minSelect$value=" + this.minSelect$value + ", maxSelect$value=" + this.maxSelect$value + ", options$value=" + this.options$value + ")";
        }
    }

    public static OptionGroup.OptionGroupBuilder builder() {
        return new OptionGroup.OptionGroupBuilder();
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

    public int getMinSelect() {
        return this.minSelect;
    }

    public int getMaxSelect() {
        return this.maxSelect;
    }

    public List<ProductOption> getOptions() {
        return this.options;
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

    public void setMinSelect(int minSelect) {
        this.minSelect = minSelect;
    }

    public void setMaxSelect(int maxSelect) {
        this.maxSelect = maxSelect;
    }

    public void setOptions(List<ProductOption> options) {
        this.options = options;
    }

    public OptionGroup() {
        this.minSelect = OptionGroup.$default$minSelect();
        this.maxSelect = OptionGroup.$default$maxSelect();
        this.options = OptionGroup.$default$options();
    }

    public OptionGroup(Long id, Product product, String name, int minSelect, int maxSelect, List<ProductOption> options) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.minSelect = minSelect;
        this.maxSelect = maxSelect;
        this.options = options;
    }
}
