package com.qrmenu.platform.menu.controller;

import com.qrmenu.platform.menu.dto.MenuCategoryDto;
import com.qrmenu.platform.menu.dto.ProductDto;
import com.qrmenu.platform.menu.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/menu")
public class PublicMenuController {

    private final MenuService menuService;

    public PublicMenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/business/{businessId}/categories")
    public ResponseEntity<List<MenuCategoryDto>> getCategories(@PathVariable Long businessId) {
        return ResponseEntity.ok(menuService.getCategoriesByBusinessId(businessId));
    }

    @GetMapping("/category/{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getProducts(@PathVariable Long categoryId) {
        return ResponseEntity.ok(menuService.getProductsByCategoryId(categoryId));
    }
}
