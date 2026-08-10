package com.qrmenu.platform.menu.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VariantRequest {
        @NotBlank(message = "Variant name is required")
        @Size(max = 100, message = "Variant name must not exceed 100 characters")
        private String name;

        @NotNull(message = "Variant price is required")
        private BigDecimal price;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionGroupRequest {
        @NotBlank(message = "Option group name is required")
        @Size(max = 100, message = "Option group name must not exceed 100 characters")
        private String name;

        private int minSelect;
        private int maxSelect;

        @Valid
        private List<OptionRequest> options;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionRequest {
        @NotBlank(message = "Option name is required")
        @Size(max = 100, message = "Option name must not exceed 100 characters")
        private String name;

        private BigDecimal priceModifier;
    }
}
