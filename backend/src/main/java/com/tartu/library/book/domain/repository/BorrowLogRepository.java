package com.tartu.library.book.domain.repository;

import com.tartu.library.book.domain.model.BorrowLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BorrowLogRepository extends JpaRepository<BorrowLog, UUID> {}
