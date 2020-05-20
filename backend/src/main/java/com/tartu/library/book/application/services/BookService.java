package com.tartu.library.book.application.services;

import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.domain.model.BookItem;
import com.tartu.library.book.domain.repository.BookEntryRepository;
import com.tartu.library.book.domain.repository.BookItemRepository;
import com.tartu.library.person.domain.model.Person;
import com.tartu.library.person.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
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
    Person person = Person.of(partialBookItemDTO.getOwner());
    BookItem bookItem = BookItem.of(bookEntry, person, partialBookItemDTO.getSerialNumber());

    bookEntryRepository.save(bookEntry);
    personRepository.save(person);
    bookItemRepository.save(bookItem);

    return bookItemAssembler.toModel(bookItem);
  }
}
