package com.qrmenu.platform.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private int sortOrder;
    private boolean isActive;
    private Integer calories;
    private Integer prepTimeMinutes;
    private List<String> tags;
    private List<ProductVariantDto> variants;
    private List<OptionGroupDto> optionGroups;
    private Instant createdAt;
    private Instant updatedAt;
}
