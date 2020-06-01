package com.tartu.library.book.rest;

import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.application.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/book/item")
public class BookItemRestController {

  Logger logger = LoggerFactory.getLogger(BookItemRestController.class);

  @Autowired BookService bookService;

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

  @DeleteMapping("{uuid}")
  public ResponseEntity<BookItemDTO> deleteBookItem(@PathVariable UUID uuid) {
    logger.info(String.format("Deleting Book Item (%s)", uuid.toString()));
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @PatchMapping("{uuid}/borrow")
  public ResponseEntity<BookItemDTO> borrowBook(@PathVariable UUID uuid) {
    logger.info(String.format("Borrowing Book Item (%s)", uuid.toString()));
    return null;
  }

  @PatchMapping("{uuid}/return")
  public ResponseEntity<BookItemDTO> returnBook(@PathVariable UUID uuid) {
    logger.info(String.format("Returning Book Item (%s)", uuid.toString()));
    return null;
  }
}
