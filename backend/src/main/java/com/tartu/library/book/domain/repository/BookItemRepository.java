package com.tartu.library.book.domain.repository;

import com.tartu.library.book.domain.model.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookItemRepository extends JpaRepository<BookItem, UUID> {}
