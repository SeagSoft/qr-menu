package com.qrmenu.platform.business;

import com.qrmenu.platform.business.dto.BusinessDto;
import com.qrmenu.platform.business.dto.CreateBusinessRequest;
import com.qrmenu.platform.business.dto.UpdateBusinessRequest;
import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.business.mapper.BusinessMapper;
import com.qrmenu.platform.business.repository.BusinessRepository;
import com.qrmenu.platform.business.service.BusinessService;
import com.qrmenu.platform.common.exception.BusinessRuleException;
import com.qrmenu.platform.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessServiceTest {

    @Mock
    private BusinessRepository businessRepository;

    @Mock
    private BusinessMapper businessMapper;

    @InjectMocks
    private BusinessService businessService;

    private Business business;
    private BusinessDto businessDto;

    @BeforeEach
    void setUp() {
        business = Business.builder()
                .id(1L)
                .name("Burger Joint")
                .slug("burger-joint")
                .ownerEmail("owner@burgerjoint.com")
                .phone("123456789")
                .isActive(true)
                .build();

        businessDto = BusinessDto.builder()
                .id(1L)
                .name("Burger Joint")
                .slug("burger-joint")
                .ownerEmail("owner@burgerjoint.com")
                .phone("123456789")
                .isActive(true)
                .build();
    }

    @Test
    void getById_ShouldReturnBusinessDto_WhenExists() {
        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));
        when(businessMapper.toDto(business)).thenReturn(businessDto);

        BusinessDto result = businessService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Burger Joint");
    }

    @Test
    void getById_ShouldThrowResourceNotFoundException_WhenNotExists() {
        when(businessRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> businessService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Business not found");
    }

    @Test
    void create_ShouldSaveBusiness_WhenSlugIsUnique() {
        CreateBusinessRequest request = CreateBusinessRequest.builder()
                .name("Burger Joint")
                .slug("burger-joint")
                .ownerEmail("owner@burgerjoint.com")
                .phone("123456789")
                .build();

        when(businessRepository.existsBySlug("burger-joint")).thenReturn(false);
        when(businessMapper.toEntity(request)).thenReturn(business);
        when(businessRepository.save(business)).thenReturn(business);
        when(businessMapper.toDto(business)).thenReturn(businessDto);

        BusinessDto result = businessService.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getSlug()).isEqualTo("burger-joint");
        verify(businessRepository).save(any(Business.class));
    }

    @Test
    void create_ShouldThrowBusinessRuleException_WhenSlugExists() {
        CreateBusinessRequest request = CreateBusinessRequest.builder()
                .slug("burger-joint")
                .build();

        when(businessRepository.existsBySlug("burger-joint")).thenReturn(true);

        assertThatThrownBy(() -> businessService.create(request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Business slug already exists");
    }

    @Test
    void update_ShouldModifyBusiness_WhenExists() {
        UpdateBusinessRequest request = UpdateBusinessRequest.builder()
                .name("New Burger Joint")
                .ownerEmail("new@owner.com")
                .phone("987654321")
                .build();

        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));
        doAnswer(invocation -> {
            business.setName(request.getName());
            business.setOwnerEmail(request.getOwnerEmail());
            business.setPhone(request.getPhone());
            return null;
        }).when(businessMapper).updateEntityFromRequest(eq(request), eq(business));

        businessDto.setName("New Burger Joint");
        when(businessRepository.save(business)).thenReturn(business);
        when(businessMapper.toDto(business)).thenReturn(businessDto);

        BusinessDto result = businessService.update(1L, request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("New Burger Joint");
    }
}
