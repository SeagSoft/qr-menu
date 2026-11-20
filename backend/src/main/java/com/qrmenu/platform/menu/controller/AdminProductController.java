package com.qrmenu.platform.menu.controller;

import com.qrmenu.platform.menu.dto.CreateProductRequest;
import com.qrmenu.platform.menu.dto.ProductDto;
import com.qrmenu.platform.menu.dto.UpdateProductRequest;
import com.qrmenu.platform.menu.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final MenuService menuService;

    public AdminProductController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(menuService.getProductsByCategoryId(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.createProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(menuService.updateProduct(id, request));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ProductDto> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.toggleProductActive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
