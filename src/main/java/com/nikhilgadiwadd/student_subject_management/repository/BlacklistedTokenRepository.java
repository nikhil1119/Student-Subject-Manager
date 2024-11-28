package com.nikhilgadiwadd.student_subject_management.repository;

import com.nikhilgadiwadd.student_subject_management.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, String> {
    boolean existsByToken(String token);
}

