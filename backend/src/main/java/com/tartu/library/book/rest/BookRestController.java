package com.tartu.library.book.rest;

import com.tartu.library.book.application.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class BookRestController {
    @Autowired
    BookService bookService;
}
