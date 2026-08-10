package com.qrmenu.platform.menu.mapper;

import com.qrmenu.platform.menu.dto.CreateCategoryRequest;
import com.qrmenu.platform.menu.dto.MenuCategoryDto;
import com.qrmenu.platform.menu.dto.UpdateCategoryRequest;
import com.qrmenu.platform.menu.entity.MenuCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface MenuCategoryMapper {

    @Mapping(source = "business.id", target = "businessId")
    MenuCategoryDto toDto(MenuCategory category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "business", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    MenuCategory toEntity(CreateCategoryRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "business", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateCategoryRequest request, @MappingTarget MenuCategory category);
}
