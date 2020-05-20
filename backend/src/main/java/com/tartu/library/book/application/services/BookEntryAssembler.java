package com.tartu.library.book.application.services;

import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.rest.BookRestController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BookEntryAssembler
    extends RepresentationModelAssemblerSupport<BookEntry, BookEntryDTO> {
  public BookEntryAssembler() {
    super(BookRestController.class, BookEntryDTO.class);
  }

  @Override
  public BookEntryDTO toModel(BookEntry bookEntry) {
    BookEntryDTO dto = createModelWithId(bookEntry.getId(), bookEntry);
    dto.setAuthor(bookEntry.getAuthor());
    dto.setBookName(bookEntry.getBookName());
    dto.setPublishDate(bookEntry.getPublishDate());
    dto.setISBN(bookEntry.getISBN());
    return dto;
  }
}
