package com.qrmenu.platform.table.mapper;

import com.qrmenu.platform.table.dto.RestaurantTableDto;
import com.qrmenu.platform.table.dto.CreateTableRequest;
import com.qrmenu.platform.table.dto.UpdateTableRequest;
import com.qrmenu.platform.table.entity.RestaurantTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface RestaurantTableMapper {

    @Mapping(source = "business.id", target = "businessId")
    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(target = "qrUrl", ignore = true)
    RestaurantTableDto toDto(RestaurantTable table);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "business", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "qrToken", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RestaurantTable toEntity(CreateTableRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "business", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "qrToken", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateTableRequest request, @MappingTarget RestaurantTable table);
}
