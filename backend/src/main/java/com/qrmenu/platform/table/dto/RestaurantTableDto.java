package com.qrmenu.platform.table.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTableDto {
    private Long id;
    private Long businessId;
    private Long branchId;
    private String tableNumber;
    private String qrToken;
    private String qrUrl; // Absolute path to scan QR code
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
