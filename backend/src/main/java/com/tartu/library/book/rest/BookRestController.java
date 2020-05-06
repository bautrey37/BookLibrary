package com.tartu.library.book.rest;

import com.tartu.library.book.application.services.BookService;
import com.tartu.library.book.domain.model.BookEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookRestController {
    Logger logger = LoggerFactory.getLogger(BookRestController.class);

    @Autowired
    BookService bookService;

    @GetMapping
    public CollectionModel<BookEntry> retrieveAllBooks() {
        logger.info("Retrieving all books");
        List<BookEntry> books = bookService.retrieveAllBooks();
        return new CollectionModel<>(books);
    }

    @PostMapping
    public BookEntry createBook() {
        return null;
    }
}
