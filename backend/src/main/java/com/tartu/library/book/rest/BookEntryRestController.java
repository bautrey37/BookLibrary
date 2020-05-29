package com.tartu.library.book.rest;

import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.application.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/book")
public class BookEntryRestController {
  Logger logger = LoggerFactory.getLogger(BookEntryRestController.class);

  @Autowired BookService bookService;

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

  @GetMapping("{uuid}")
  public BookEntryDTO retrieveBook(@PathVariable UUID uuid) {
    logger.info(String.format("Retrieving Book Entry (%s)", uuid.toString()));
    return bookService.retrieveBookEntryDTO(uuid);
  }
}
