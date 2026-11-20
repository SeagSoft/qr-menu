package com.qrmenu.platform.business.entity;

import com.qrmenu.platform.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "business")
public class Business extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;
    @Column(name = "phone")
    private String phone;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    private static boolean $default$isActive() {
        return true;
    }


    public static class BusinessBuilder {
        private Long id;
        private String name;
        private String slug;
        private String ownerEmail;
        private String phone;
        private boolean isActive$set;
        private boolean isActive$value;

        BusinessBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Business.BusinessBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Business.BusinessBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Business.BusinessBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Business.BusinessBuilder ownerEmail(String ownerEmail) {
            this.ownerEmail = ownerEmail;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Business.BusinessBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Business.BusinessBuilder isActive(boolean isActive) {
            this.isActive$value = isActive;
            isActive$set = true;
            return this;
        }

        public Business build() {
            boolean isActive$value = this.isActive$value;
            if (!this.isActive$set) isActive$value = Business.$default$isActive();
            return new Business(this.id, this.name, this.slug, this.ownerEmail, this.phone, isActive$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Business.BusinessBuilder(id=" + this.id + ", name=" + this.name + ", slug=" + this.slug + ", ownerEmail=" + this.ownerEmail + ", phone=" + this.phone + ", isActive$value=" + this.isActive$value + ")";
        }
    }

    public static Business.BusinessBuilder builder() {
        return new Business.BusinessBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSlug() {
        return this.slug;
    }

    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    public String getPhone() {
        return this.phone;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Business() {
        this.isActive = Business.$default$isActive();
    }

    public Business(Long id, String name, String slug, String ownerEmail, String phone, boolean isActive) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.ownerEmail = ownerEmail;
        this.phone = phone;
        this.isActive = isActive;
    }
}
