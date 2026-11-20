package com.qrmenu.platform.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateBusinessRequest {
    @NotBlank(message = "Business name is required")
    @Size(max = 255, message = "Business name must not exceed 255 characters")
    private String name;
    @NotBlank(message = "Slug is required")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens")
    @Size(max = 100, message = "Slug must not exceed 100 characters")
    private String slug;
    @NotBlank(message = "Owner email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Owner email must not exceed 255 characters")
    private String ownerEmail;
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;


    public static class CreateBusinessRequestBuilder {
        private String name;
        private String slug;
        private String ownerEmail;
        private String phone;

        CreateBusinessRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public CreateBusinessRequest.CreateBusinessRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateBusinessRequest.CreateBusinessRequestBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateBusinessRequest.CreateBusinessRequestBuilder ownerEmail(String ownerEmail) {
            this.ownerEmail = ownerEmail;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateBusinessRequest.CreateBusinessRequestBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public CreateBusinessRequest build() {
            return new CreateBusinessRequest(this.name, this.slug, this.ownerEmail, this.phone);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "CreateBusinessRequest.CreateBusinessRequestBuilder(name=" + this.name + ", slug=" + this.slug + ", ownerEmail=" + this.ownerEmail + ", phone=" + this.phone + ")";
        }
    }

    public static CreateBusinessRequest.CreateBusinessRequestBuilder builder() {
        return new CreateBusinessRequest.CreateBusinessRequestBuilder();
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

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateBusinessRequest)) return false;
        CreateBusinessRequest other = (CreateBusinessRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
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
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof CreateBusinessRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        java.lang.Object $slug = this.getSlug();
        result = result * PRIME + ($slug == null ? 43 : $slug.hashCode());
        java.lang.Object $ownerEmail = this.getOwnerEmail();
        result = result * PRIME + ($ownerEmail == null ? 43 : $ownerEmail.hashCode());
        java.lang.Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "CreateBusinessRequest(name=" + this.getName() + ", slug=" + this.getSlug() + ", ownerEmail=" + this.getOwnerEmail() + ", phone=" + this.getPhone() + ")";
    }

    public CreateBusinessRequest() {
    }

    public CreateBusinessRequest(String name, String slug, String ownerEmail, String phone) {
        this.name = name;
        this.slug = slug;
        this.ownerEmail = ownerEmail;
        this.phone = phone;
    }
}
