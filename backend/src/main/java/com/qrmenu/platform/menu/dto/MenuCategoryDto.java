package com.qrmenu.platform.menu.dto;

import java.time.Instant;

public class MenuCategoryDto {
    private Long id;
    private Long businessId;
    private String name;
    private String description;
    private int sortOrder;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;


    public static class MenuCategoryDtoBuilder {
        private Long id;
        private Long businessId;
        private String name;
        private String description;
        private int sortOrder;
        private boolean isActive;
        private Instant createdAt;
        private Instant updatedAt;

        MenuCategoryDtoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public MenuCategoryDto.MenuCategoryDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategoryDto.MenuCategoryDtoBuilder businessId(Long businessId) {
            this.businessId = businessId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategoryDto.MenuCategoryDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategoryDto.MenuCategoryDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategoryDto.MenuCategoryDtoBuilder sortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategoryDto.MenuCategoryDtoBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategoryDto.MenuCategoryDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public MenuCategoryDto.MenuCategoryDtoBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public MenuCategoryDto build() {
            return new MenuCategoryDto(this.id, this.businessId, this.name, this.description, this.sortOrder, this.isActive, this.createdAt, this.updatedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "MenuCategoryDto.MenuCategoryDtoBuilder(id=" + this.id + ", businessId=" + this.businessId + ", name=" + this.name + ", description=" + this.description + ", sortOrder=" + this.sortOrder + ", isActive=" + this.isActive + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }

    public static MenuCategoryDto.MenuCategoryDtoBuilder builder() {
        return new MenuCategoryDto.MenuCategoryDtoBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Long getBusinessId() {
        return this.businessId;
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

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
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

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof MenuCategoryDto)) return false;
        MenuCategoryDto other = (MenuCategoryDto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getSortOrder() != other.getSortOrder()) return false;
        if (this.isActive() != other.isActive()) return false;
        java.lang.Object this$id = this.getId();
        java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        java.lang.Object this$businessId = this.getBusinessId();
        java.lang.Object other$businessId = other.getBusinessId();
        if (this$businessId == null ? other$businessId != null : !this$businessId.equals(other$businessId)) return false;
        java.lang.Object this$name = this.getName();
        java.lang.Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        java.lang.Object this$description = this.getDescription();
        java.lang.Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        java.lang.Object this$createdAt = this.getCreatedAt();
        java.lang.Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        java.lang.Object this$updatedAt = this.getUpdatedAt();
        java.lang.Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof MenuCategoryDto;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getSortOrder();
        result = result * PRIME + (this.isActive() ? 79 : 97);
        java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        java.lang.Object $businessId = this.getBusinessId();
        result = result * PRIME + ($businessId == null ? 43 : $businessId.hashCode());
        java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        java.lang.Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        java.lang.Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        java.lang.Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "MenuCategoryDto(id=" + this.getId() + ", businessId=" + this.getBusinessId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", sortOrder=" + this.getSortOrder() + ", isActive=" + this.isActive() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public MenuCategoryDto() {
    }

    public MenuCategoryDto(Long id, Long businessId, String name, String description, int sortOrder, boolean isActive, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.businessId = businessId;
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
