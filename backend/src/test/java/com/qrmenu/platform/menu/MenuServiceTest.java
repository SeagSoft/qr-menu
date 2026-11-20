package com.qrmenu.platform.menu;

import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.business.repository.BusinessRepository;
import com.qrmenu.platform.common.context.TenantContext;
import com.qrmenu.platform.common.exception.BusinessRuleException;
import com.qrmenu.platform.menu.dto.CreateCategoryRequest;
import com.qrmenu.platform.menu.dto.CreateProductRequest;
import com.qrmenu.platform.menu.dto.MenuCategoryDto;
import com.qrmenu.platform.menu.dto.ProductDto;
import com.qrmenu.platform.menu.entity.MenuCategory;
import com.qrmenu.platform.menu.entity.Product;
import com.qrmenu.platform.menu.mapper.MenuCategoryMapper;
import com.qrmenu.platform.menu.mapper.ProductMapper;
import com.qrmenu.platform.menu.repository.MenuCategoryRepository;
import com.qrmenu.platform.menu.repository.ProductRepository;
import com.qrmenu.platform.menu.service.MenuService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuCategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BusinessRepository businessRepository;

    @Mock
    private MenuCategoryMapper categoryMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private MenuService menuService;

    private Business business;
    private MenuCategory category;
    private MenuCategoryDto categoryDto;
    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        business = Business.builder()
                .id(1L)
                .name("Burger Joint")
                .slug("burger-joint")
                .build();

        category = MenuCategory.builder()
                .id(10L)
                .business(business)
                .name("Burgers")
                .sortOrder(1)
                .isActive(true)
                .build();

        categoryDto = MenuCategoryDto.builder()
                .id(10L)
                .businessId(1L)
                .name("Burgers")
                .sortOrder(1)
                .isActive(true)
                .build();

        product = Product.builder()
                .id(100L)
                .category(category)
                .name("Classic Burger")
                .price(BigDecimal.valueOf(9.99))
                .isActive(true)
                .build();

        productDto = ProductDto.builder()
                .id(100L)
                .categoryId(10L)
                .name("Classic Burger")
                .price(BigDecimal.valueOf(9.99))
                .isActive(true)
                .build();
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void createCategory_ShouldSaveCategory_WhenValid() {
        TenantContext.setBusinessId(1L);
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .businessId(1L)
                .name("Burgers")
                .build();

        when(categoryRepository.existsByBusinessIdAndName(1L, "Burgers")).thenReturn(false);
        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));
        when(categoryMapper.toEntity(request)).thenReturn(category);
        when(categoryRepository.save(any(MenuCategory.class))).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        MenuCategoryDto result = menuService.createCategory(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Burgers");
        verify(categoryRepository).save(any(MenuCategory.class));
    }

    @Test
    void createCategory_ShouldThrowBusinessRuleException_WhenDuplicateName() {
        TenantContext.setBusinessId(1L);
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .businessId(1L)
                .name("Burgers")
                .build();

        when(categoryRepository.existsByBusinessIdAndName(1L, "Burgers")).thenReturn(true);

        assertThatThrownBy(() -> menuService.createCategory(request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Category name already exists");
    }

    @Test
    void createProduct_ShouldSaveProduct_WhenValid() {
        TenantContext.setBusinessId(1L);
        CreateProductRequest request = CreateProductRequest.builder()
                .categoryId(10L)
                .name("Classic Burger")
                .price(BigDecimal.valueOf(9.99))
                .build();

        when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
        when(productRepository.existsByCategoryIdAndName(10L, "Classic Burger")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);

        ProductDto result = menuService.createProduct(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Classic Burger");
        verify(productRepository).save(any(Product.class));
    }
}
