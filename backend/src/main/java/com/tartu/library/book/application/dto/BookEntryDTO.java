package com.tartu.library.book.application.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
public class BookEntryDTO extends RepresentationModel<BookEntryDTO> {
    String ISBN;
    String bookName;
    String author;
    LocalDate publishDate;
}
