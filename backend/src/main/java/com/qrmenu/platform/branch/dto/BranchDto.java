package com.qrmenu.platform.branch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private Long id;
    private Long businessId;
    private String name;
    private String address;
    private String phone;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
