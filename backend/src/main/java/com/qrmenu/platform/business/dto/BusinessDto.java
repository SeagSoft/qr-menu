package com.qrmenu.platform.business.dto;

import java.time.Instant;

public class BusinessDto {
    private Long id;
    private String name;
    private String slug;
    private String ownerEmail;
    private String phone;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;


    public static class BusinessDtoBuilder {
        private Long id;
        private String name;
        private String slug;
        private String ownerEmail;
        private String phone;
        private boolean isActive;
        private Instant createdAt;
        private Instant updatedAt;

        BusinessDtoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public BusinessDto.BusinessDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public BusinessDto.BusinessDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public BusinessDto.BusinessDtoBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public BusinessDto.BusinessDtoBuilder ownerEmail(String ownerEmail) {
            this.ownerEmail = ownerEmail;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public BusinessDto.BusinessDtoBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public BusinessDto.BusinessDtoBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public BusinessDto.BusinessDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public BusinessDto.BusinessDtoBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public BusinessDto build() {
            return new BusinessDto(this.id, this.name, this.slug, this.ownerEmail, this.phone, this.isActive, this.createdAt, this.updatedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "BusinessDto.BusinessDtoBuilder(id=" + this.id + ", name=" + this.name + ", slug=" + this.slug + ", ownerEmail=" + this.ownerEmail + ", phone=" + this.phone + ", isActive=" + this.isActive + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }

    public static BusinessDto.BusinessDtoBuilder builder() {
        return new BusinessDto.BusinessDtoBuilder();
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

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
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

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof BusinessDto)) return false;
        BusinessDto other = (BusinessDto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.isActive() != other.isActive()) return false;
        java.lang.Object this$id = this.getId();
        java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        java.lang.Object this$name = this.getName();
        java.lang.Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        java.lang.Object this$slug = this.getSlug();
        java.lang.Object other$slug = other.getSlug();
        if (this$slug == null ? other$slug != null : !this$slug.equals(other$slug)) return false;
        java.lang.Object this$ownerEmail = this.getOwnerEmail();
        java.lang.Object other$ownerEmail = other.getOwnerEmail();
        if (this$ownerEmail == null ? other$ownerEmail != null : !this$ownerEmail.equals(other$ownerEmail)) return false;
        java.lang.Object this$phone = this.getPhone();
        java.lang.Object other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) return false;
        java.lang.Object this$createdAt = this.getCreatedAt();
        java.lang.Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        java.lang.Object this$updatedAt = this.getUpdatedAt();
        java.lang.Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof BusinessDto;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isActive() ? 79 : 97);
        java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        java.lang.Object $slug = this.getSlug();
        result = result * PRIME + ($slug == null ? 43 : $slug.hashCode());
        java.lang.Object $ownerEmail = this.getOwnerEmail();
        result = result * PRIME + ($ownerEmail == null ? 43 : $ownerEmail.hashCode());
        java.lang.Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        java.lang.Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        java.lang.Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "BusinessDto(id=" + this.getId() + ", name=" + this.getName() + ", slug=" + this.getSlug() + ", ownerEmail=" + this.getOwnerEmail() + ", phone=" + this.getPhone() + ", isActive=" + this.isActive() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public BusinessDto() {
    }

    public BusinessDto(Long id, String name, String slug, String ownerEmail, String phone, boolean isActive, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.ownerEmail = ownerEmail;
        this.phone = phone;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
