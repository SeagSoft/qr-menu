package com.qrmenu.platform.auth.service;

import com.qrmenu.platform.auth.entity.StaffUser;
import com.qrmenu.platform.auth.model.StaffPrincipal;
import com.qrmenu.platform.auth.repository.StaffUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StaffUserRepository staffUserRepository;

    public CustomUserDetailsService(StaffUserRepository staffUserRepository) {
        this.staffUserRepository = staffUserRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        StaffUser user = staffUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Staff user not found with email: " + email));

        if (!user.isActive()) {
            throw new UsernameNotFoundException("Staff user is inactive: " + email);
        }

        return new StaffPrincipal(user);
    }
}
