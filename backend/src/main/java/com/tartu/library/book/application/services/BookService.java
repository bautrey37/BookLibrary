package com.tartu.library.book.application.services;

import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.application.dto.BorrowLogDTO;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.domain.model.BookItem;
import com.tartu.library.book.domain.model.BookStatus;
import com.tartu.library.book.domain.model.BorrowLog;
import com.tartu.library.book.domain.repository.BookEntryRepository;
import com.tartu.library.book.domain.repository.BookItemRepository;
import com.tartu.library.book.domain.repository.BorrowLogRepository;
import com.tartu.library.common.application.exception.EntityNotFoundException;
import com.tartu.library.common.application.exception.InvalidBookStatusException;
import com.tartu.library.person.application.services.PersonService;
import com.tartu.library.person.domain.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
  Logger logger = LoggerFactory.getLogger(BookService.class);

  @Autowired BookEntryRepository bookEntryRepository;

  @Autowired BookItemRepository bookItemRepository;

  @Autowired BorrowLogRepository borrowLogRepository;

  @Autowired BookEntryAssembler bookEntryAssembler;

  @Autowired BookItemAssembler bookItemAssembler;

  @Autowired BorrowLogAssembler borrowLogAssembler;

  @Autowired PersonService personService;

  public CollectionModel<BookEntryDTO> retrieveAllBooks() {
    List<BookEntry> books = bookEntryRepository.findAll();
    return bookEntryAssembler.toCollectionModel(books);
  }

  public CollectionModel<BookItemDTO> retrieveAllBookItems() {
    List<BookItem> items = bookItemRepository.findAll();
    return bookItemAssembler.toCollectionModel(items);
  }

  public BookItemDTO createBookInLibrary(BookItemDTO partialBookItemDTO) {
    BookEntry bookEntry = BookEntry.of(partialBookItemDTO.getBookInfo());
    if (bookEntryRepository.existsByName(bookEntry.getBookName())) {
      logger.info(String.format("BookEntry already exists. Name: (%s)", bookEntry.getBookName()));
      bookEntry = bookEntryRepository.findByName(bookEntry.getBookName());
      bookEntryRepository.save(bookEntry.incrementBookItemsCount());
    } else {
      bookEntryRepository.save(bookEntry);
    }

    Person person = personService.createPersonIfNotExist(partialBookItemDTO.getOwner());

    BookItem bookItem = BookItem.of(bookEntry, person, partialBookItemDTO.getSerialNumber());
    bookItemRepository.save(bookItem);

    return bookItemAssembler.toModel(bookItem);
  }

  public BookItemDTO retrieveBookItemDTO(UUID uuid) {
    BookItem item = retrieveBookItem(uuid);
    return bookItemAssembler.toModel(item);
  }

  public void deleteBookItem(UUID uuid) {
    BookItem item = retrieveBookItem(uuid);
    bookItemRepository.delete(item);
    BookEntry entry = retrieveBookEntry(item.getBookInfo().getId());
    bookEntryRepository.save(entry.decrementBookItemsCount());
  }

  public BookEntryDTO retrieveBookEntryDTO(UUID uuid) {
    BookEntry entry = retrieveBookEntry(uuid);
    return bookEntryAssembler.toModel(entry);
  }

  public CollectionModel<BookItemDTO> retrieveBookItemsByBookEntry(UUID entry_uuid) {
    List<BookItem> items = bookItemRepository.retrieveBookItemsByBookEntry(entry_uuid);
    return bookItemAssembler.toCollectionModel(items);
  }

  public BookItemDTO borrowBook(UUID book_item_uuid, UUID person_uuid)
      throws InvalidBookStatusException {
    BookItem item = retrieveBookItem(book_item_uuid);
    if (item.getStatus() == BookStatus.BORROWED) {
      throw new InvalidBookStatusException("Cannot borrow book already borrowed.");
    }
    Person person = personService.retrievePerson(person_uuid);
    item.setBorrower(person);
    item.setStatus(BookStatus.BORROWED);
    bookItemRepository.save(item);

    createBorrowLog(item, person, BookStatus.BORROWED);

    return bookItemAssembler.toModel(item);
  }

  public BookItemDTO returnBook(UUID book_item_uuid) throws InvalidBookStatusException {
    BookItem item = retrieveBookItem(book_item_uuid);
    if (item.getStatus() == BookStatus.AVAILABLE) {
      throw new InvalidBookStatusException("Cannot return book that is available.");
    }
    Person borrower = personService.retrievePerson(item.getBorrower().getId());
    item.setStatus(BookStatus.AVAILABLE);
    item.setBorrower(null);
    bookItemRepository.save(item);

    createBorrowLog(item, borrower, BookStatus.AVAILABLE);

    return bookItemAssembler.toModel(item);
  }

  public CollectionModel<BorrowLogDTO> retrieveBorrowLogsByBookItem(UUID uuid) {
    List<BorrowLog> logs = borrowLogRepository.findBorrowLogsByBookItem(uuid);
    return borrowLogAssembler.toCollectionModel(logs);
  }

  private void createBorrowLog(BookItem item, Person borrower, BookStatus status) {
    BorrowLog log = BorrowLog.of(item, borrower, status);
    borrowLogRepository.save(log);
  }

  private BookItem retrieveBookItem(UUID uuid) {
    return bookItemRepository
        .findById(uuid)
        .orElseThrow(() -> new EntityNotFoundException(BookItem.class, "uuid", uuid.toString()));
  }

  private BookEntry retrieveBookEntry(UUID uuid) {
    return bookEntryRepository
        .findById(uuid)
        .orElseThrow(() -> new EntityNotFoundException(BookEntry.class, "uuid", uuid.toString()));
  }
}
