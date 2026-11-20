package com.qrmenu.platform.auth.mapper;

import com.qrmenu.platform.auth.dto.RegisterStaffRequest;
import com.qrmenu.platform.auth.dto.StaffUserDto;
import com.qrmenu.platform.auth.entity.StaffUser;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface StaffUserMapper {

    @Mapping(source = "business.id", target = "businessId")
    @Mapping(source = "branch.id", target = "branchId")
    StaffUserDto toDto(StaffUser staffUser);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "business", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StaffUser toEntity(RegisterStaffRequest request);
}
