package com.qrmenu.platform.menu.controller;

import com.qrmenu.platform.menu.dto.CreateCategoryRequest;
import com.qrmenu.platform.menu.dto.MenuCategoryDto;
import com.qrmenu.platform.menu.dto.UpdateCategoryRequest;
import com.qrmenu.platform.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final MenuService menuService;

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<MenuCategoryDto>> getByBusinessId(@PathVariable Long businessId) {
        return ResponseEntity.ok(menuService.getCategoriesByBusinessId(businessId));
    }

    @PostMapping
    public ResponseEntity<MenuCategoryDto> create(@Valid @RequestBody CreateCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.createCategory(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuCategoryDto> update(@PathVariable Long id, @Valid @RequestBody UpdateCategoryRequest request) {
        return ResponseEntity.ok(menuService.updateCategory(id, request));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<MenuCategoryDto> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.toggleCategoryActive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
