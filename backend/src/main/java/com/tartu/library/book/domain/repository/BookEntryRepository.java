package com.tartu.library.book.domain.repository;

import com.tartu.library.book.domain.model.BookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookEntryRepository extends JpaRepository<BookEntry, UUID> {
    @Query("select b from BookEntry b where b.bookName like %?1%")
    public BookEntry findByName(String name);
}
