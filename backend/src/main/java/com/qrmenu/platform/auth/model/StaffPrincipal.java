package com.qrmenu.platform.auth.model;

import com.qrmenu.platform.auth.entity.StaffUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class StaffPrincipal implements UserDetails {
    private final Long id;
    private final Long businessId;
    private final Long branchId;
    private final String email;
    private final String password;
    private final Role role;
    private final Collection<? extends GrantedAuthority> authorities;

    public StaffPrincipal(StaffUser user) {
        this.id = user.getId();
        this.businessId = user.getBusiness().getId();
        this.branchId = user.getBranch() != null ? user.getBranch().getId() : null;
        this.email = user.getEmail();
        this.password = user.getPasswordHash();
        this.role = user.getRole();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return this.id;
    }

    public Long getBusinessId() {
        return this.businessId;
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public String getEmail() {
        return this.email;
    }

    public Role getRole() {
        return this.role;
    }
}
