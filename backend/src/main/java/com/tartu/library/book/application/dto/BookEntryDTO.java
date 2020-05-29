package com.tartu.library.book.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class BookEntryDTO extends RepresentationModel<BookEntryDTO> {
  String ISBN;
  String bookName;
  String author;
  LocalDate publishDate;
}
