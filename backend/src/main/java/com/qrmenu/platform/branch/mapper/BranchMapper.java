package com.qrmenu.platform.branch.mapper;

import com.qrmenu.platform.branch.dto.BranchDto;
import com.qrmenu.platform.branch.dto.CreateBranchRequest;
import com.qrmenu.platform.branch.dto.UpdateBranchRequest;
import com.qrmenu.platform.branch.entity.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface BranchMapper {

    @Mapping(source = "business.id", target = "businessId")
    BranchDto toDto(Branch branch);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "business", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Branch toEntity(CreateBranchRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "business", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateBranchRequest request, @MappingTarget Branch branch);
}
