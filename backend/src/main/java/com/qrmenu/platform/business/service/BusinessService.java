package com.qrmenu.platform.business.service;

import com.qrmenu.platform.business.dto.BusinessDto;
import com.qrmenu.platform.business.dto.CreateBusinessRequest;
import com.qrmenu.platform.business.dto.UpdateBusinessRequest;
import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.business.mapper.BusinessMapper;
import com.qrmenu.platform.business.repository.BusinessRepository;
import com.qrmenu.platform.common.exception.BusinessRuleException;
import com.qrmenu.platform.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;

    @Transactional(readOnly = true)
    public BusinessDto getById(Long id) {
        return businessRepository.findById(id)
                .map(businessMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Business", id));
    }

    @Transactional(readOnly = true)
    public BusinessDto getBySlug(String slug) {
        return businessRepository.findBySlug(slug)
                .map(businessMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Business", slug));
    }

    @Transactional
    public BusinessDto create(CreateBusinessRequest request) {
        if (businessRepository.existsBySlug(request.getSlug())) {
            throw new BusinessRuleException("DUPLICATE_SLUG", "Business slug already exists: " + request.getSlug());
        }

        Business business = businessMapper.toEntity(request);
        Business saved = businessRepository.save(business);
        return businessMapper.toDto(saved);
    }

    @Transactional
    public BusinessDto update(Long id, UpdateBusinessRequest request) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business", id));

        businessMapper.updateEntityFromRequest(request, business);
        Business saved = businessRepository.save(business);
        return businessMapper.toDto(saved);
    }

    @Transactional
    public BusinessDto toggleActive(Long id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business", id));

        business.setActive(!business.isActive());
        Business saved = businessRepository.save(business);
        return businessMapper.toDto(saved);
    }
}
