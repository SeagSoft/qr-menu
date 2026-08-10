package com.qrmenu.platform.auth;

import com.qrmenu.platform.auth.dto.AuthResponse;
import com.qrmenu.platform.auth.dto.LoginRequest;
import com.qrmenu.platform.auth.dto.RegisterStaffRequest;
import com.qrmenu.platform.auth.dto.StaffUserDto;
import com.qrmenu.platform.auth.entity.StaffUser;
import com.qrmenu.platform.auth.jwt.JwtTokenProvider;
import com.qrmenu.platform.auth.mapper.StaffUserMapper;
import com.qrmenu.platform.auth.model.Role;
import com.qrmenu.platform.auth.model.StaffPrincipal;
import com.qrmenu.platform.auth.repository.StaffUserRepository;
import com.qrmenu.platform.auth.service.AuthService;
import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.branch.repository.BranchRepository;
import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.business.repository.BusinessRepository;
import com.qrmenu.platform.common.exception.BusinessRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private StaffUserRepository staffUserRepository;

    @Mock
    private BusinessRepository businessRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private StaffUserMapper staffUserMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthService authService;

    private Business business;
    private Branch branch;
    private StaffUser staffUser;
    private StaffUserDto staffUserDto;

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
                .name("Downtown")
                .build();

        staffUser = StaffUser.builder()
                .id(100L)
                .business(business)
                .branch(branch)
                .email("admin@burgerjoint.com")
                .passwordHash("hashed_password")
                .firstName("John")
                .lastName("Doe")
                .role(Role.ADMIN)
                .isActive(true)
                .build();

        staffUserDto = StaffUserDto.builder()
                .id(100L)
                .businessId(1L)
                .branchId(10L)
                .email("admin@burgerjoint.com")
                .firstName("John")
                .lastName("Doe")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
    }

    @Test
    void login_ShouldReturnAuthResponse_WhenCredentialsAreValid() {
        LoginRequest request = LoginRequest.builder()
                .email("admin@burgerjoint.com")
                .password("password")
                .build();

        Authentication authentication = mock(Authentication.class);
        StaffPrincipal principal = new StaffPrincipal(staffUser);
        when(authentication.getPrincipal()).thenReturn(principal);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(tokenProvider.generateToken(eq("admin@burgerjoint.com"), eq(1L), eq(10L), eq("ADMIN")))
                .thenReturn("mock_jwt_token");

        when(staffUserRepository.findByEmail("admin@burgerjoint.com")).thenReturn(Optional.of(staffUser));
        when(staffUserMapper.toDto(staffUser)).thenReturn(staffUserDto);

        AuthResponse response = authService.login(request);

        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("mock_jwt_token");
        assertThat(response.getUser().getEmail()).isEqualTo("admin@burgerjoint.com");
    }

    @Test
    void register_ShouldSaveStaffUser_WhenRequestIsValid() {
        RegisterStaffRequest request = RegisterStaffRequest.builder()
                .businessId(1L)
                .branchId(10L)
                .email("newadmin@burgerjoint.com")
                .password("securepassword")
                .firstName("Jane")
                .lastName("Doe")
                .role(Role.ADMIN)
                .build();

        when(staffUserRepository.existsByEmail("newadmin@burgerjoint.com")).thenReturn(false);
        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));
        when(branchRepository.findById(10L)).thenReturn(Optional.of(branch));
        when(staffUserMapper.toEntity(request)).thenReturn(staffUser);
        when(passwordEncoder.encode("securepassword")).thenReturn("hashed_password");
        when(staffUserRepository.save(any(StaffUser.class))).thenReturn(staffUser);
        when(staffUserMapper.toDto(staffUser)).thenReturn(staffUserDto);

        StaffUserDto result = authService.register(request);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("admin@burgerjoint.com");
        verify(staffUserRepository).save(any(StaffUser.class));
    }

    @Test
    void register_ShouldThrowBusinessRuleException_WhenEmailExists() {
        RegisterStaffRequest request = RegisterStaffRequest.builder()
                .email("admin@burgerjoint.com")
                .build();

        when(staffUserRepository.existsByEmail("admin@burgerjoint.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Email address already registered");
    }
}
