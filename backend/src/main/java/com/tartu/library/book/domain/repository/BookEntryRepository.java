package com.tartu.library.book.domain.repository;

import com.tartu.library.book.domain.model.BookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookEntryRepository extends JpaRepository<BookEntry, UUID> {
  @Query(
      "select case when count(b)> 0 then true else false end from BookEntry b where b.bookName like %?1%")
  boolean existsByName(String name);

  @Query("select b from BookEntry b where b.bookName like %?1%")
  BookEntry findByName(String bookName);
}
