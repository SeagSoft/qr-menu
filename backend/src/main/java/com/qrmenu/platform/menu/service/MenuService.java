package com.qrmenu.platform.menu.service;

import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.business.repository.BusinessRepository;
import com.qrmenu.platform.common.context.TenantContext;
import com.qrmenu.platform.common.exception.AccessDeniedException;
import com.qrmenu.platform.common.exception.BusinessRuleException;
import com.qrmenu.platform.common.exception.ResourceNotFoundException;
import com.qrmenu.platform.menu.dto.*;
import com.qrmenu.platform.menu.entity.*;
import com.qrmenu.platform.menu.mapper.MenuCategoryMapper;
import com.qrmenu.platform.menu.mapper.ProductMapper;
import com.qrmenu.platform.menu.repository.MenuCategoryRepository;
import com.qrmenu.platform.menu.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuCategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final BusinessRepository businessRepository;
    private final MenuCategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    // --- Category Management ---

    @Transactional(readOnly = true)
    public List<MenuCategoryDto> getCategoriesByBusinessId(Long businessId) {
        verifyTenantAccess(businessId);
        return categoryRepository.findByBusinessIdOrderBySortOrderAsc(businessId)
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Transactional
    public MenuCategoryDto createCategory(CreateCategoryRequest request) {
        verifyTenantAccess(request.getBusinessId());

        if (categoryRepository.existsByBusinessIdAndName(request.getBusinessId(), request.getName())) {
            throw new BusinessRuleException("DUPLICATE_CATEGORY_NAME", "Category name already exists: " + request.getName());
        }

        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new ResourceNotFoundException("Business", request.getBusinessId()));

        MenuCategory category = categoryMapper.toEntity(request);
        category.setBusiness(business);

        MenuCategory saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    @Transactional
    public MenuCategoryDto updateCategory(Long id, UpdateCategoryRequest request) {
        MenuCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", id));

        verifyTenantAccess(category.getBusiness().getId());

        if (!category.getName().equals(request.getName()) &&
                categoryRepository.existsByBusinessIdAndName(category.getBusiness().getId(), request.getName())) {
            throw new BusinessRuleException("DUPLICATE_CATEGORY_NAME", "Category name already exists: " + request.getName());
        }

        categoryMapper.updateEntityFromRequest(request, category);
        MenuCategory saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    @Transactional
    public MenuCategoryDto toggleCategoryActive(Long id) {
        MenuCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", id));

        verifyTenantAccess(category.getBusiness().getId());

        category.setActive(!category.isActive());
        MenuCategory saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    @Transactional
    public void deleteCategory(Long id) {
        MenuCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", id));

        verifyTenantAccess(category.getBusiness().getId());
        categoryRepository.delete(category);
    }

    // --- Product Management ---

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        MenuCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", categoryId));

        verifyTenantAccess(category.getBusiness().getId());

        return productRepository.findByCategoryIdOrderBySortOrderAsc(categoryId)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        verifyTenantAccess(product.getCategory().getBusiness().getId());
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto createProduct(CreateProductRequest request) {
        MenuCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", request.getCategoryId()));

        verifyTenantAccess(category.getBusiness().getId());

        if (productRepository.existsByCategoryIdAndName(request.getCategoryId(), request.getName())) {
            throw new BusinessRuleException("DUPLICATE_PRODUCT_NAME", "Product name already exists in this category: " + request.getName());
        }

        Product product = Product.builder()
                .category(category)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .sortOrder(request.getSortOrder())
                .calories(request.getCalories())
                .prepTimeMinutes(request.getPrepTimeMinutes())
                .tags(request.getTags() != null ? new ArrayList<>(request.getTags()) : new ArrayList<>())
                .isActive(true)
                .variants(new ArrayList<>())
                .optionGroups(new ArrayList<>())
                .build();

        // Map Variants
        if (request.getVariants() != null) {
            for (CreateProductRequest.VariantRequest vReq : request.getVariants()) {
                ProductVariant variant = ProductVariant.builder()
                        .product(product)
                        .name(vReq.getName())
                        .price(vReq.getPrice())
                        .build();
                product.getVariants().add(variant);
            }
        }

        // Map Option Groups & Options
        if (request.getOptionGroups() != null) {
            for (CreateProductRequest.OptionGroupRequest ogReq : request.getOptionGroups()) {
                OptionGroup group = OptionGroup.builder()
                        .product(product)
                        .name(ogReq.getName())
                        .minSelect(ogReq.getMinSelect())
                        .maxSelect(ogReq.getMaxSelect())
                        .options(new ArrayList<>())
                        .build();

                if (ogReq.getOptions() != null) {
                    for (CreateProductRequest.OptionRequest oReq : ogReq.getOptions()) {
                        ProductOption option = ProductOption.builder()
                                .optionGroup(group)
                                .name(oReq.getName())
                                .priceModifier(oReq.getPriceModifier())
                                .build();
                        group.getOptions().add(option);
                    }
                }
                product.getOptionGroups().add(group);
            }
        }

        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Transactional
    public ProductDto updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        verifyTenantAccess(product.getCategory().getBusiness().getId());

        if (!product.getName().equals(request.getName()) &&
                productRepository.existsByCategoryIdAndName(product.getCategory().getId(), request.getName())) {
            throw new BusinessRuleException("DUPLICATE_PRODUCT_NAME", "Product name already exists in this category: " + request.getName());
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setSortOrder(request.getSortOrder());
        product.setCalories(request.getCalories());
        product.setPrepTimeMinutes(request.getPrepTimeMinutes());
        if (request.getTags() != null) {
            product.setTags(new ArrayList<>(request.getTags()));
        }

        // Update Variants (Overwriting strategy for simplicity)
        product.getVariants().clear();
        if (request.getVariants() != null) {
            for (CreateProductRequest.VariantRequest vReq : request.getVariants()) {
                ProductVariant variant = ProductVariant.builder()
                        .product(product)
                        .name(vReq.getName())
                        .price(vReq.getPrice())
                        .build();
                product.getVariants().add(variant);
            }
        }

        // Update Option Groups & Options (Overwriting strategy for simplicity)
        product.getOptionGroups().clear();
        if (request.getOptionGroups() != null) {
            for (CreateProductRequest.OptionGroupRequest ogReq : request.getOptionGroups()) {
                OptionGroup group = OptionGroup.builder()
                        .product(product)
                        .name(ogReq.getName())
                        .minSelect(ogReq.getMinSelect())
                        .maxSelect(ogReq.getMaxSelect())
                        .options(new ArrayList<>())
                        .build();

                if (ogReq.getOptions() != null) {
                    for (CreateProductRequest.OptionRequest oReq : ogReq.getOptions()) {
                        ProductOption option = ProductOption.builder()
                                .optionGroup(group)
                                .name(oReq.getName())
                                .priceModifier(oReq.getPriceModifier())
                                .build();
                        group.getOptions().add(option);
                    }
                }
                product.getOptionGroups().add(group);
            }
        }

        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Transactional
    public ProductDto toggleProductActive(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        verifyTenantAccess(product.getCategory().getBusiness().getId());

        product.setActive(!product.isActive());
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        verifyTenantAccess(product.getCategory().getBusiness().getId());
        productRepository.delete(product);
    }

    private void verifyTenantAccess(Long businessId) {
        Long currentTenant = TenantContext.getBusinessId();
        if (currentTenant != null && !currentTenant.equals(businessId)) {
            throw new AccessDeniedException("Unauthorized access to tenant business");
        }
    }
}
