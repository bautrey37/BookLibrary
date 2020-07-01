package com.tartu.library.book.application.services;

import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.rest.BookEntryRestController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookEntryAssembler
    extends RepresentationModelAssemblerSupport<BookEntry, BookEntryDTO> {
  public BookEntryAssembler() {
    super(BookEntryRestController.class, BookEntryDTO.class);
  }

  @Override
  public BookEntryDTO toModel(BookEntry bookEntry) {
    BookEntryDTO dto = createModelWithId(bookEntry.getId(), bookEntry);
    dto.setAuthor(bookEntry.getAuthor());
    dto.setBookName(bookEntry.getBookName());
    dto.setPublishDate(bookEntry.getPublishDate());
    dto.setISBN(bookEntry.getISBN());
    dto.setNumberOfBookItems(bookEntry.getNumberOfBookItems());

    dto.add(
        linkTo(
                methodOn(BookEntryRestController.class)
                    .retrieveBookItemsByBookEntry(bookEntry.getId()))
            .withRel("items")
            .withType(HttpMethod.GET.toString()));
    return dto;
  }
}
