package com.qrmenu.platform.auth.service;

import com.qrmenu.platform.auth.dto.AuthResponse;
import com.qrmenu.platform.auth.dto.LoginRequest;
import com.qrmenu.platform.auth.dto.RegisterStaffRequest;
import com.qrmenu.platform.auth.dto.StaffUserDto;
import com.qrmenu.platform.auth.entity.StaffUser;
import com.qrmenu.platform.auth.jwt.JwtTokenProvider;
import com.qrmenu.platform.auth.mapper.StaffUserMapper;
import com.qrmenu.platform.auth.model.StaffPrincipal;
import com.qrmenu.platform.auth.repository.StaffUserRepository;
import com.qrmenu.platform.branch.entity.Branch;
import com.qrmenu.platform.branch.repository.BranchRepository;
import com.qrmenu.platform.business.entity.Business;
import com.qrmenu.platform.business.repository.BusinessRepository;
import com.qrmenu.platform.common.exception.BusinessRuleException;
import com.qrmenu.platform.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final StaffUserRepository staffUserRepository;
    private final BusinessRepository businessRepository;
    private final BranchRepository branchRepository;
    private final StaffUserMapper staffUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        StaffPrincipal principal = (StaffPrincipal) authentication.getPrincipal();

        String token = tokenProvider.generateToken(
                principal.getEmail(),
                principal.getBusinessId(),
                principal.getBranchId(),
                principal.getRole().name()
        );

        StaffUser staffUser = staffUserRepository.findByEmail(principal.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("StaffUser", principal.getEmail()));

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .user(staffUserMapper.toDto(staffUser))
                .build();
    }

    @Transactional
    public StaffUserDto register(RegisterStaffRequest request) {
        if (staffUserRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("DUPLICATE_EMAIL", "Email address already registered: " + request.getEmail());
        }

        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new ResourceNotFoundException("Business", request.getBusinessId()));

        Branch branch = null;
        if (request.getBranchId() != null) {
            branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch", request.getBranchId()));
            if (!branch.getBusiness().getId().equals(business.getId())) {
                throw new BusinessRuleException("INVALID_BRANCH", "Branch does not belong to specified Business");
            }
        }

        StaffUser user = staffUserMapper.toEntity(request);
        user.setBusiness(business);
        user.setBranch(branch);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        StaffUser saved = staffUserRepository.save(user);
        return staffUserMapper.toDto(saved);
    }
}
