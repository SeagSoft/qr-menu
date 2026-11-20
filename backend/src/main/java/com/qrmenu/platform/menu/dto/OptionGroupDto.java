package com.qrmenu.platform.menu.dto;

import java.util.List;

public class OptionGroupDto {
    private Long id;
    private String name;
    private int minSelect;
    private int maxSelect;
    private List<ProductOptionDto> options;


    public static class OptionGroupDtoBuilder {
        private Long id;
        private String name;
        private int minSelect;
        private int maxSelect;
        private List<ProductOptionDto> options;

        OptionGroupDtoBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public OptionGroupDto.OptionGroupDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OptionGroupDto.OptionGroupDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OptionGroupDto.OptionGroupDtoBuilder minSelect(int minSelect) {
            this.minSelect = minSelect;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OptionGroupDto.OptionGroupDtoBuilder maxSelect(int maxSelect) {
            this.maxSelect = maxSelect;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public OptionGroupDto.OptionGroupDtoBuilder options(List<ProductOptionDto> options) {
            this.options = options;
            return this;
        }

        public OptionGroupDto build() {
            return new OptionGroupDto(this.id, this.name, this.minSelect, this.maxSelect, this.options);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "OptionGroupDto.OptionGroupDtoBuilder(id=" + this.id + ", name=" + this.name + ", minSelect=" + this.minSelect + ", maxSelect=" + this.maxSelect + ", options=" + this.options + ")";
        }
    }

    public static OptionGroupDto.OptionGroupDtoBuilder builder() {
        return new OptionGroupDto.OptionGroupDtoBuilder();
    }

    public Long getId() {
        return this.id;
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

    public List<ProductOptionDto> getOptions() {
        return this.options;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setOptions(List<ProductOptionDto> options) {
        this.options = options;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof OptionGroupDto)) return false;
        OptionGroupDto other = (OptionGroupDto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getMinSelect() != other.getMinSelect()) return false;
        if (this.getMaxSelect() != other.getMaxSelect()) return false;
        java.lang.Object this$id = this.getId();
        java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        java.lang.Object this$name = this.getName();
        java.lang.Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        java.lang.Object this$options = this.getOptions();
        java.lang.Object other$options = other.getOptions();
        if (this$options == null ? other$options != null : !this$options.equals(other$options)) return false;
        return true;
    }

    protected boolean canEqual(java.lang.Object other) {
        return other instanceof OptionGroupDto;
    }

    @java.lang.Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getMinSelect();
        result = result * PRIME + this.getMaxSelect();
        java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        java.lang.Object $options = this.getOptions();
        result = result * PRIME + ($options == null ? 43 : $options.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "OptionGroupDto(id=" + this.getId() + ", name=" + this.getName() + ", minSelect=" + this.getMinSelect() + ", maxSelect=" + this.getMaxSelect() + ", options=" + this.getOptions() + ")";
    }

    public OptionGroupDto() {
    }

    public OptionGroupDto(Long id, String name, int minSelect, int maxSelect, List<ProductOptionDto> options) {
        this.id = id;
        this.name = name;
        this.minSelect = minSelect;
        this.maxSelect = maxSelect;
        this.options = options;
    }
}
