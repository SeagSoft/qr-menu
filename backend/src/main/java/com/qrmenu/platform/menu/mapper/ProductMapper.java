package com.qrmenu.platform.menu.mapper;

import com.qrmenu.platform.menu.dto.OptionGroupDto;
import com.qrmenu.platform.menu.dto.ProductDto;
import com.qrmenu.platform.menu.dto.ProductOptionDto;
import com.qrmenu.platform.menu.dto.ProductVariantDto;
import com.qrmenu.platform.menu.entity.OptionGroup;
import com.qrmenu.platform.menu.entity.Product;
import com.qrmenu.platform.menu.entity.ProductOption;
import com.qrmenu.platform.menu.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    ProductVariantDto toDto(ProductVariant variant);

    OptionGroupDto toDto(OptionGroup optionGroup);

    ProductOptionDto toDto(ProductOption option);
}
