package com.tartu.library.book.application.services;

import com.tartu.library.book.domain.repository.BookEntryRepository;
import com.tartu.library.book.domain.repository.BookItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    BookEntryRepository bookEntryRepository;

    @Autowired
    BookItemRepository bookItemRepository;


}
