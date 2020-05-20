package com.tartu.library.book.rest;

import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.application.services.BookService;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.domain.model.BookItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    public CollectionModel<BookEntryDTO> retrieveAllBooks() {
        logger.info("Retrieving all books");
        return bookService.retrieveAllBooks();
    }

    @PostMapping
    public BookItemDTO createBook(@RequestBody @Validated BookItemDTO partialBookItemDTO) {
        logger.info("Creating book in library");
        return bookService.createBookInLibrary(partialBookItemDTO);
    }

    @GetMapping("/item")
    public CollectionModel<BookItemDTO> retrieveAllBookItems() {
        logger.info("Retrieving all book items");
        return bookService.retrieveAllBookItems();
    }
}
