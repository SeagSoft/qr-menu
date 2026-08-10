package com.qrmenu.platform.branch;

import com.qrmenu.platform.branch.dto.BranchDto;
import com.qrmenu.platform.branch.dto.CreateBranchRequest;
import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.branch.mapper.BranchMapper;
import com.qrmenu.platform.branch.repository.BranchRepository;
import com.qrmenu.platform.branch.service.BranchService;
import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.business.repository.BusinessRepository;
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
class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private BusinessRepository businessRepository;

    @Mock
    private BranchMapper branchMapper;

    @InjectMocks
    private BranchService branchService;

    private Business business;
    private Branch branch;
    private BranchDto branchDto;

    @BeforeEach
    void setUp() {
        business = Business.builder()
                .id(1L)
                .name("Burger Joint")
                .slug("burger-joint")
                .build();

        branch = Branch.builder()
                .id(10L)
                .business(business)
                .name("Downtown Branch")
                .address("123 Main St")
                .isActive(true)
                .build();

        branchDto = BranchDto.builder()
                .id(10L)
                .businessId(1L)
                .name("Downtown Branch")
                .address("123 Main St")
                .isActive(true)
                .build();
    }

    @Test
    void getById_ShouldReturnBranchDto_WhenExists() {
        when(branchRepository.findById(10L)).thenReturn(Optional.of(branch));
        when(branchMapper.toDto(branch)).thenReturn(branchDto);

        BranchDto result = branchService.getById(10L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo("Downtown Branch");
    }

    @Test
    void getById_ShouldThrowResourceNotFoundException_WhenNotExists() {
        when(branchRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> branchService.getById(10L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Branch not found");
    }

    @Test
    void create_ShouldSaveBranch_WhenBusinessExists() {
        CreateBranchRequest request = CreateBranchRequest.builder()
                .businessId(1L)
                .name("Downtown Branch")
                .address("123 Main St")
                .build();

        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));
        when(branchMapper.toEntity(request)).thenReturn(branch);
        when(branchRepository.save(branch)).thenReturn(branch);
        when(branchMapper.toDto(branch)).thenReturn(branchDto);

        BranchDto result = branchService.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Downtown Branch");
        verify(branchRepository).save(any(Branch.class));
    }

    @Test
    void create_ShouldThrowResourceNotFoundException_WhenBusinessDoesNotExist() {
        CreateBranchRequest request = CreateBranchRequest.builder()
                .businessId(1L)
                .name("Downtown Branch")
                .build();

        when(businessRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> branchService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Business not found");
    }
}
