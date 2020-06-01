package com.tartu.library.book.domain.repository;

import com.tartu.library.book.domain.model.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookItemRepository extends JpaRepository<BookItem, UUID> {
    @Query("select i from BookItem i where i.bookInfo.id = ?1")
    List<BookItem> retrieveBookItemsByBookEntry(UUID entry_uuid);
}
