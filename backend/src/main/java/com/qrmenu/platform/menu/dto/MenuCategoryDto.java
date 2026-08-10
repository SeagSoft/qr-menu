package com.qrmenu.platform.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryDto {
    private Long id;
    private Long businessId;
    private String name;
    private String description;
    private int sortOrder;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
