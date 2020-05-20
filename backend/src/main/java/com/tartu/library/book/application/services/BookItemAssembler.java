package com.tartu.library.book.application.services;

import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.domain.model.BookItem;
import com.tartu.library.book.rest.BookItemRestController;
import com.tartu.library.person.application.services.PersonAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BookItemAssembler extends RepresentationModelAssemblerSupport<BookItem, BookItemDTO> {

  @Autowired BookEntryAssembler bookEntryAssembler;

  @Autowired PersonAssembler personAssembler;

  public BookItemAssembler() {
    super(BookItemRestController.class, BookItemDTO.class);
  }

  @Override
  public BookItemDTO toModel(BookItem bookItem) {
    BookItemDTO dto = createModelWithId(bookItem.getId(), bookItem);
    dto.setBookInfo(bookEntryAssembler.toModel(bookItem.getBookInfo()));
    dto.setSerialNumber(bookItem.getSerialNumber());
    dto.setOwner(personAssembler.toModel(bookItem.getOwner()));
    return dto;
  }
}
