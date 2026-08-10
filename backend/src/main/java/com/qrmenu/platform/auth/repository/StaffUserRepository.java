package com.qrmenu.platform.auth.repository;

import com.qrmenu.platform.auth.entity.StaffUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffUserRepository extends JpaRepository<StaffUser, Long> {
    Optional<StaffUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
