package com.qrmenu.platform.auth.controller;

import com.qrmenu.platform.auth.dto.AuthResponse;
import com.qrmenu.platform.auth.dto.LoginRequest;
import com.qrmenu.platform.auth.dto.RegisterStaffRequest;
import com.qrmenu.platform.auth.dto.StaffUserDto;
import com.qrmenu.platform.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<StaffUserDto> register(@Valid @RequestBody RegisterStaffRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
}
