package com.tartu.library.book.rest;

import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.application.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/book/item")
public class BookItemRestController {

  Logger logger = LoggerFactory.getLogger(BookItemRestController.class);

  BookService bookService;

  @GetMapping
  public CollectionModel<BookItemDTO> retrieveAllBookItems() {
    logger.info("Retrieving all book items");
    return bookService.retrieveAllBookItems();
  }

  @GetMapping("{uuid}")
  public BookItemDTO retrieveBookItem(@PathVariable UUID uuid) {
    logger.info(String.format("Retrieving Book Item (%s)", uuid.toString()));
    return bookService.retrieveBookItemDTO(uuid);
  }
}
