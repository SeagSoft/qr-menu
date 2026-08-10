package com.qrmenu.platform.business.mapper;

import com.qrmenu.platform.business.dto.BusinessDto;
import com.qrmenu.platform.business.dto.CreateBusinessRequest;
import com.qrmenu.platform.business.dto.UpdateBusinessRequest;
import com.qrmenu.platform.business.entity.Business;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface BusinessMapper {

    BusinessDto toDto(Business business);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Business toEntity(CreateBusinessRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateBusinessRequest request, @MappingTarget Business business);
}
