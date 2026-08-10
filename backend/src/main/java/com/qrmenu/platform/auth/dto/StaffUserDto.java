package com.qrmenu.platform.auth.dto;

import com.qrmenu.platform.auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffUserDto {
    private Long id;
    private Long businessId;
    private Long branchId;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
