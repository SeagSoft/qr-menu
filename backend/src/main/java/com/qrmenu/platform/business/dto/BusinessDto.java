package com.qrmenu.platform.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDto {
    private Long id;
    private String name;
    private String slug;
    private String ownerEmail;
    private String phone;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
