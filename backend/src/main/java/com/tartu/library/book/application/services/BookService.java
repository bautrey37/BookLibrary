package com.tartu.library.book.application.services;

import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.domain.repository.BookEntryRepository;
import com.tartu.library.book.domain.repository.BookItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookEntryRepository bookEntryRepository;

    @Autowired
    BookItemRepository bookItemRepository;


    public List<BookEntry> retrieveAllBooks() {
        return bookEntryRepository.findAll();
    }
}
