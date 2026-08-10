package com.qrmenu.platform.table;

import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.branch.repository.BranchRepository;
import com.qrmenu.platform.business.entity.Business;
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
import com.qrmenu.platform.table.service.RestaurantTableService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTableServiceTest {

    @Mock
    private RestaurantTableRepository tableRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private RestaurantTableMapper tableMapper;

    @InjectMocks
    private RestaurantTableService tableService;

    private Business business;
    private Branch branch;
    private RestaurantTable table;
    private RestaurantTableDto tableDto;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tableService, "baseUrl", "http://localhost:3000");

        business = Business.builder()
                .id(1L)
                .name("Burger Joint")
                .slug("burger-joint")
                .build();

        branch = Branch.builder()
                .id(10L)
                .business(business)
                .name("Downtown")
                .build();

        table = RestaurantTable.builder()
                .id(100L)
                .business(business)
                .branch(branch)
                .tableNumber("T-01")
                .qrToken("xyz123")
                .isActive(true)
                .build();

        tableDto = RestaurantTableDto.builder()
                .id(100L)
                .businessId(1L)
                .branchId(10L)
                .tableNumber("T-01")
                .qrToken("xyz123")
                .isActive(true)
                .build();
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void getById_ShouldReturnTable_WhenExistsAndTenantMatches() {
        TenantContext.setBusinessId(1L);
        when(tableRepository.findById(100L)).thenReturn(Optional.of(table));
        when(tableMapper.toDto(table)).thenReturn(tableDto);

        RestaurantTableDto result = tableService.getById(100L);

        assertThat(result).isNotNull();
        assertThat(result.getTableNumber()).isEqualTo("T-01");
        assertThat(result.getQrUrl()).isEqualTo("http://localhost:3000/t/xyz123");
    }

    @Test
    void getById_ShouldThrowAccessDeniedException_WhenTenantMismatch() {
        TenantContext.setBusinessId(2L); // Different tenant
        when(tableRepository.findById(100L)).thenReturn(Optional.of(table));

        assertThatThrownBy(() -> tableService.getById(100L))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessageContaining("Unauthorized access to tenant business");
    }

    @Test
    void create_ShouldSaveTable_WhenValidAndUniqueNumber() {
        TenantContext.setBusinessId(1L);
        CreateTableRequest request = CreateTableRequest.builder()
                .branchId(10L)
                .tableNumber("T-02")
                .build();

        when(branchRepository.findById(10L)).thenReturn(Optional.of(branch));
        when(tableRepository.existsByBranchIdAndTableNumber(10L, "T-02")).thenReturn(false);
        when(tableRepository.existsByQrToken(anyString())).thenReturn(false);
        when(tableMapper.toEntity(request)).thenReturn(table);
        when(tableRepository.save(any(RestaurantTable.class))).thenReturn(table);
        when(tableMapper.toDto(table)).thenReturn(tableDto);

        RestaurantTableDto result = tableService.create(request);

        assertThat(result).isNotNull();
        verify(tableRepository).save(any(RestaurantTable.class));
    }

    @Test
    void create_ShouldThrowBusinessRuleException_WhenTableNumberExists() {
        TenantContext.setBusinessId(1L);
        CreateTableRequest request = CreateTableRequest.builder()
                .branchId(10L)
                .tableNumber("T-01")
                .build();

        when(branchRepository.findById(10L)).thenReturn(Optional.of(branch));
        when(tableRepository.existsByBranchIdAndTableNumber(10L, "T-01")).thenReturn(true);

        assertThatThrownBy(() -> tableService.create(request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Table number already exists in this branch");
    }
}
