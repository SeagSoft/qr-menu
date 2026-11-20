package com.qrmenu.platform.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateCategoryRequest {
    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Category name must not exceed 100 characters")
    private String name;
    private String description;
    private int sortOrder;


    public static class UpdateCategoryRequestBuilder {
        private String name;
        private String description;
        private int sortOrder;

        UpdateCategoryRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public UpdateCategoryRequest.UpdateCategoryRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateCategoryRequest.UpdateCategoryRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateCategoryRequest.UpdateCategoryRequestBuilder sortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public UpdateCategoryRequest build() {
            return new UpdateCategoryRequest(this.name, this.description, this.sortOrder);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "UpdateCategoryRequest.UpdateCategoryRequestBuilder(name=" + this.name + ", description=" + this.description + ", sortOrder=" + this.sortOrder + ")";
        }
    }

    public static UpdateCategoryRequest.UpdateCategoryRequestBuilder builder() {
        return new UpdateCategoryRequest.UpdateCategoryRequestBuilder();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof UpdateCategoryRequest)) return false;
        UpdateCategoryRequest other = (UpdateCategoryRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getSortOrder() != other.getSortOrder()) return false;
        java.lang.Object this$name = this.getName();
        java.lang.Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        java.lang.Object this$description = this.getDescription();
        java.lang.Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof UpdateCategoryRequest;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getSortOrder();
        java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        java.lang.Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "UpdateCategoryRequest(name=" + this.getName() + ", description=" + this.getDescription() + ", sortOrder=" + this.getSortOrder() + ")";
    }

    public UpdateCategoryRequest() {
    }

    public UpdateCategoryRequest(String name, String description, int sortOrder) {
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
    }
}
