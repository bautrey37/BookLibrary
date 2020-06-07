package com.tartu.library.book.application.services;

import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.domain.model.BookItem;
import com.tartu.library.book.rest.BookItemRestController;
import com.tartu.library.person.application.services.PersonAssembler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookItemAssembler extends RepresentationModelAssemblerSupport<BookItem, BookItemDTO> {

  @Autowired BookEntryAssembler bookEntryAssembler;

  @Autowired PersonAssembler personAssembler;

  public BookItemAssembler() {
    super(BookItemRestController.class, BookItemDTO.class);
  }

  @SneakyThrows
  @Override
  public BookItemDTO toModel(BookItem bookItem) {
    BookItemDTO dto = createModelWithId(bookItem.getId(), bookItem);
    dto.setBookInfo(bookEntryAssembler.toModel(bookItem.getBookInfo()));
    dto.setSerialNumber(bookItem.getSerialNumber());
    if (bookItem.getOwner() != null) {
      dto.setOwner(personAssembler.toModel(bookItem.getOwner()));
    }
    if (bookItem.getBorrower() != null) {
      dto.setBorrower(personAssembler.toModel(bookItem.getBorrower()));
    }
    dto.setStatus(bookItem.getStatus());

    switch (bookItem.getStatus()) {
      case AVAILABLE -> dto.add(
              linkTo(methodOn(BookItemRestController.class).borrowBook(bookItem.getId(), bookItem.getOwner().getId()))
                      .withRel("borrow")
                      .withType(HttpMethod.PATCH.toString()));
      case BORROWED -> dto.add(
              linkTo(methodOn(BookItemRestController.class).returnBook(bookItem.getId()))
                      .withRel("return")
                      .withType(HttpMethod.PATCH.toString()));
      default -> throw new IllegalArgumentException(String.format("Status not available. Status: (%s)", bookItem.getStatus()));
    }

    dto.add(
            linkTo(methodOn(BookItemRestController.class).retrieveBorrowLogs(bookItem.getId()))
                    .withRel("logs")
                    .withType(HttpMethod.GET.toString()));

    return dto;
  }
}
