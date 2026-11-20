package com.qrmenu.platform.auth.controller;

import com.qrmenu.platform.auth.dto.RegisterStaffRequest;
import com.qrmenu.platform.auth.dto.StaffUserDto;
import com.qrmenu.platform.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/staff")
public class AdminStaffController {

    private final AuthService authService;

    public AdminStaffController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<StaffUserDto>> getAllStaff(
            @RequestParam(defaultValue = "1") Long businessId
    ) {
        return ResponseEntity.ok(authService.getStaffByBusinessId(businessId));
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<StaffUserDto>> getByBusinessId(@PathVariable Long businessId) {
        return ResponseEntity.ok(authService.getStaffByBusinessId(businessId));
    }

    @PostMapping
    public ResponseEntity<StaffUserDto> createStaff(@Valid @RequestBody RegisterStaffRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<StaffUserDto> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(authService.toggleStaffActive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        authService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
