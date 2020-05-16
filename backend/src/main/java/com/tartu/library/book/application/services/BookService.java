package com.tartu.library.book.application.services;

import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.domain.model.BookItem;
import com.tartu.library.book.domain.repository.BookEntryRepository;
import com.tartu.library.book.domain.repository.BookItemRepository;
import com.tartu.library.person.domain.model.Person;
import com.tartu.library.person.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookEntryRepository bookEntryRepository;

    @Autowired
    BookItemRepository bookItemRepository;

    @Autowired
    PersonRepository personRepository;


    public List<BookEntry> retrieveAllBooks() {
        return bookEntryRepository.findAll();
    }

    public List<BookItem> retrieveAllBookItems() {
        return bookItemRepository.findAll();
    }

    public void createBookInLibrary(BookItemDTO partialBookItemDTO) {
        BookEntry bookEntry = BookEntry.of(partialBookItemDTO.getBookInfo());
        Person person = Person.of(partialBookItemDTO.getOwner());
        BookItem bookItem = BookItem.of(bookEntry, person, partialBookItemDTO.getSerialNumber());

        bookEntryRepository.save(bookEntry);
        personRepository.save(person);
        bookItemRepository.save(bookItem);
    }

}
