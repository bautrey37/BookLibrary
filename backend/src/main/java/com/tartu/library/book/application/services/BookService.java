package com.tartu.library.book.application.services;

import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.domain.model.BookItem;
import com.tartu.library.book.domain.model.BookStatus;
import com.tartu.library.book.domain.model.BorrowLog;
import com.tartu.library.book.domain.repository.BookEntryRepository;
import com.tartu.library.book.domain.repository.BookItemRepository;
import com.tartu.library.book.domain.repository.BorrowLogRepository;
import com.tartu.library.common.application.exception.EntityNotFoundException;
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

  public BookEntryDTO retrieveBookEntryDTO(UUID uuid) {
    BookEntry entry = retrieveBookEntry(uuid);
    return bookEntryAssembler.toModel(entry);
  }

  public CollectionModel<BookItemDTO> retrieveBookItemsByBookEntry(UUID entry_uuid) {
    List<BookItem> items = bookItemRepository.retrieveBookItemsByBookEntry(entry_uuid);
    return bookItemAssembler.toCollectionModel(items);
  }

  public BookItemDTO borrowBook(UUID book_item_uuid, UUID person_uuid) {
    BookItem item = retrieveBookItem(book_item_uuid);
    Person person = personService.retrievePerson(person_uuid);
    item.setBorrower(person);
    item.setStatus(BookStatus.BORROWED);
    bookItemRepository.save(item);

    BorrowLog log = BorrowLog.of(item, person);
    borrowLogRepository.save(log);

    return bookItemAssembler.toModel(item);
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
