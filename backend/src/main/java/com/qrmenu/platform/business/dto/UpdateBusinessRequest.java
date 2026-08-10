package com.qrmenu.platform.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBusinessRequest {

    @NotBlank(message = "Business name is required")
    @Size(max = 255, message = "Business name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Owner email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Owner email must not exceed 255 characters")
    private String ownerEmail;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;
}
