package com.tartu.library.book.application.services;

import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.domain.model.BookItem;
import com.tartu.library.book.domain.repository.BookEntryRepository;
import com.tartu.library.book.domain.repository.BookItemRepository;
import com.tartu.library.common.application.exception.EntityNotFoundException;
import com.tartu.library.person.domain.model.Person;
import com.tartu.library.person.domain.repository.PersonRepository;
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

  @Autowired PersonRepository personRepository;

  @Autowired BookEntryAssembler bookEntryAssembler;

  @Autowired BookItemAssembler bookItemAssembler;

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

    Person person = null;
    if (partialBookItemDTO.getOwner() != null) {
      person = Person.of(partialBookItemDTO.getOwner());
      if (personRepository.existsByName(person.getName())) {
        logger.info(String.format("Person already exists. Name: (%s)", person.getName()));
        person = personRepository.findByName(person.getName());
      } else {
        personRepository.save(person);
      }
    }

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
