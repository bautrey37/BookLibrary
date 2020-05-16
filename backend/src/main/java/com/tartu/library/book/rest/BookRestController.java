package com.tartu.library.book.rest;

import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.application.services.BookService;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.domain.model.BookItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public BookEntry createBook(@RequestBody @Validated BookItemDTO partialBookItemDTO) {
        logger.info("Creating book in library");
        bookService.createBookInLibrary(partialBookItemDTO);
        return null;
    }

    @GetMapping("/item")
    public CollectionModel<BookItem> retrieveAllBookItems() {
        logger.info("Retrieving all book items");
        List<BookItem> bookItems = bookService.retrieveAllBookItems();
        return new CollectionModel<>(bookItems);
    }
}
