package com.qrmenu.platform.table.service;

import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.branch.repository.BranchRepository;
import com.qrmenu.platform.common.context.TenantContext;
import com.qrmenu.platform.common.exception.AccessDeniedException;
import com.qrmenu.platform.common.exception.BusinessRuleException;
import com.qrmenu.platform.common.exception.ResourceNotFoundException;
import com.qrmenu.platform.table.dto.CreateTableRequest;
import com.qrmenu.platform.table.dto.RestaurantTableDto;
import com.qrmenu.platform.table.dto.UpdateTableRequest;
import com.qrmenu.platform.table.entity.RestaurantTable;
import com.qrmenu.platform.table.mapper.RestaurantTableMapper;
import com.qrmenu.platform.table.repository.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantTableService {

    private final RestaurantTableRepository tableRepository;
    private final BranchRepository branchRepository;
    private final RestaurantTableMapper tableMapper;

    public RestaurantTableService(RestaurantTableRepository tableRepository, BranchRepository branchRepository, RestaurantTableMapper tableMapper) {
        this.tableRepository = tableRepository;
        this.branchRepository = branchRepository;
        this.tableMapper = tableMapper;
    }

    @Value("${app.base-url}")
    private String baseUrl;

    @Transactional(readOnly = true)
    public RestaurantTableDto getById(Long id) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RestaurantTable", id));

        verifyTenantAccess(table.getBusiness().getId());
        return enrichWithQrUrl(tableMapper.toDto(table));
    }

    @Transactional(readOnly = true)
    public RestaurantTableDto getByQrToken(String qrToken) {
        RestaurantTable table = tableRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new ResourceNotFoundException("RestaurantTable", qrToken));

        if (!table.isActive()) {
            throw new BusinessRuleException("TABLE_INACTIVE", "Table is currently inactive");
        }

        return enrichWithQrUrl(tableMapper.toDto(table));
    }

    @Transactional(readOnly = true)
    public List<RestaurantTableDto> getByBranchId(Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .or(() -> branchRepository.findAll().stream().findFirst())
                .orElseThrow(() -> new ResourceNotFoundException("Branch", branchId));

        verifyTenantAccess(branch.getBusiness().getId());

        return tableRepository.findByBranchId(branch.getId())
                .stream()
                .map(tableMapper::toDto)
                .map(this::enrichWithQrUrl)
                .toList();
    }

    @Transactional
    public RestaurantTableDto create(CreateTableRequest request) {
        Branch branch = branchRepository.findById(request.getBranchId())
                .or(() -> branchRepository.findAll().stream().findFirst())
                .orElseThrow(() -> new ResourceNotFoundException("Branch", request.getBranchId()));

        verifyTenantAccess(branch.getBusiness().getId());

        if (tableRepository.existsByBranchIdAndTableNumber(branch.getId(), request.getTableNumber())) {
            throw new BusinessRuleException("DUPLICATE_TABLE_NUMBER",
                    "Table number already exists in this branch: " + request.getTableNumber());
        }

        RestaurantTable table = tableMapper.toEntity(request);
        table.setBusiness(branch.getBusiness());
        table.setBranch(branch);
        table.setQrToken(generateUniqueQrToken());

        RestaurantTable saved = tableRepository.save(table);
        return enrichWithQrUrl(tableMapper.toDto(saved));
    }

    @Transactional
    public RestaurantTableDto update(Long id, UpdateTableRequest request) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RestaurantTable", id));

        verifyTenantAccess(table.getBusiness().getId());

        if (!table.getTableNumber().equals(request.getTableNumber()) &&
                tableRepository.existsByBranchIdAndTableNumber(table.getBranch().getId(), request.getTableNumber())) {
            throw new BusinessRuleException("DUPLICATE_TABLE_NUMBER",
                    "Table number already exists in this branch: " + request.getTableNumber());
        }

        tableMapper.updateEntityFromRequest(request, table);
        RestaurantTable saved = tableRepository.save(table);
        return enrichWithQrUrl(tableMapper.toDto(saved));
    }

    @Transactional
    public RestaurantTableDto toggleActive(Long id) {
        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RestaurantTable", id));

        verifyTenantAccess(table.getBusiness().getId());

        table.setActive(!table.isActive());
        RestaurantTable saved = tableRepository.save(table);
        return enrichWithQrUrl(tableMapper.toDto(saved));
    }

    private void verifyTenantAccess(Long businessId) {
        Long currentTenant = TenantContext.getBusinessId();
        // If context has a businessId set (from JWT), check match. If not (e.g. system setup), allow.
        if (currentTenant != null && !currentTenant.equals(businessId)) {
            throw new AccessDeniedException("Unauthorized access to tenant business");
        }
    }

    private String generateUniqueQrToken() {
        String token;
        do {
            token = UUID.randomUUID().toString().replace("-", "");
        } while (tableRepository.existsByQrToken(token));
        return token;
    }

    private RestaurantTableDto enrichWithQrUrl(RestaurantTableDto dto) {
        dto.setQrUrl(String.format("%s/t/%s", baseUrl, dto.getQrToken()));
        return dto;
    }
}
