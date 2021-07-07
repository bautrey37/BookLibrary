package com.tartu.library.auth.domain.repository;

import com.tartu.library.auth.domain.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
  AuthUser findByUsername(String username);
}
