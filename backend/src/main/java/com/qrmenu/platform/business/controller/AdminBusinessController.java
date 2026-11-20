package com.qrmenu.platform.business.controller;

import com.qrmenu.platform.business.dto.BusinessDto;
import com.qrmenu.platform.business.dto.CreateBusinessRequest;
import com.qrmenu.platform.business.dto.UpdateBusinessRequest;
import com.qrmenu.platform.business.service.BusinessService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/business")
public class AdminBusinessController {

    private final BusinessService businessService;

    public AdminBusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping
    public ResponseEntity<BusinessDto> create(@Valid @RequestBody CreateBusinessRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(businessService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(businessService.getById(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<BusinessDto> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(businessService.getBySlug(slug));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessDto> update(@PathVariable Long id, @Valid @RequestBody UpdateBusinessRequest request) {
        return ResponseEntity.ok(businessService.update(id, request));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<BusinessDto> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(businessService.toggleActive(id));
    }
}
