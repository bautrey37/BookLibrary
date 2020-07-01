package com.tartu.library.book.application.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class BookEntryDTO extends RepresentationModel<BookEntryDTO> {
  String ISBN;
  String bookName;
  String author;
  LocalDate publishDate;
  Integer numberOfBookItems;
}
