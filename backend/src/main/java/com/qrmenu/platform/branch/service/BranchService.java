package com.qrmenu.platform.branch.service;

import com.qrmenu.platform.branch.dto.BranchDto;
import com.qrmenu.platform.branch.dto.CreateBranchRequest;
import com.qrmenu.platform.branch.dto.UpdateBranchRequest;
import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.branch.mapper.BranchMapper;
import com.qrmenu.platform.branch.repository.BranchRepository;
import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.business.repository.BusinessRepository;
import com.qrmenu.platform.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final BusinessRepository businessRepository;
    private final BranchMapper branchMapper;

    public BranchService(BranchRepository branchRepository, BusinessRepository businessRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.businessRepository = businessRepository;
        this.branchMapper = branchMapper;
    }

    @Transactional(readOnly = true)
    public BranchDto getById(Long id) {
        return branchRepository.findById(id)
                .map(branchMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", id));
    }

    @Transactional(readOnly = true)
    public List<BranchDto> getByBusinessId(Long businessId) {
        if (!businessRepository.existsById(businessId)) {
            throw new ResourceNotFoundException("Business", businessId);
        }
        return branchRepository.findByBusinessId(businessId)
                .stream()
                .map(branchMapper::toDto)
                .toList();
    }

    @Transactional
    public BranchDto create(CreateBranchRequest request) {
        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new ResourceNotFoundException("Business", request.getBusinessId()));

        Branch branch = branchMapper.toEntity(request);
        branch.setBusiness(business);
        Branch saved = branchRepository.save(branch);
        return branchMapper.toDto(saved);
    }

    @Transactional
    public BranchDto update(Long id, UpdateBranchRequest request) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", id));

        branchMapper.updateEntityFromRequest(request, branch);
        Branch saved = branchRepository.save(branch);
        return branchMapper.toDto(saved);
    }

    @Transactional
    public BranchDto toggleActive(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", id));

        branch.setActive(!branch.isActive());
        Branch saved = branchRepository.save(branch);
        return branchMapper.toDto(saved);
    }
}
