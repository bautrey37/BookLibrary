package com.tartu.library.book.domain.repository;

import com.tartu.library.book.domain.model.BorrowLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowLogRepository extends JpaRepository<BorrowLog, UUID> {
  @Query("select log from BorrowLog log where log.item.id = ?1")
  List<BorrowLog> findBorrowLogsByBookItem(UUID item_uuid);
}
