package com.tartu.library.auth.domain.repository;

import com.tartu.library.auth.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {}
