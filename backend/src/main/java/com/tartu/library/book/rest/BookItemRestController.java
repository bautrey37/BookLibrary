package com.tartu.library.book.rest;

import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.application.dto.BorrowLogDTO;
import com.tartu.library.book.application.services.BookService;
import com.tartu.library.common.application.exception.InvalidBookStatusException;
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
  public BookItemDTO borrowBook(@PathVariable UUID uuid, @RequestParam UUID person_uuid) throws InvalidBookStatusException {
    logger.info(String.format("Borrowing Book Item (%s)", uuid.toString()));
    return bookService.borrowBook(uuid, person_uuid);
  }

  @PatchMapping("{uuid}/return")
  public BookItemDTO returnBook(@PathVariable UUID uuid) throws InvalidBookStatusException {
    logger.info(String.format("Returning Book Item (%s)", uuid.toString()));
    return bookService.returnBook(uuid);
  }

  @GetMapping("{uuid}/logs")
  public CollectionModel<BorrowLogDTO> retrieveBorrowLogs(@PathVariable UUID uuid) {
    logger.info(String.format("Retrieving Borrow Logs from Book Item (%s)", uuid.toString()));
    return bookService.retrieveBorrowLogsByBookItem(uuid);
  }

}
