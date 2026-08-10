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
public class UpdateProductRequest {

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
    private List<CreateProductRequest.VariantRequest> variants;

    @Valid
    private List<CreateProductRequest.OptionGroupRequest> optionGroups;
}
