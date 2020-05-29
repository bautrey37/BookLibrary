package com.tartu.library.book.application.dto;

import com.tartu.library.person.application.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class BookItemDTO extends RepresentationModel<BookItemDTO> {
  String serialNumber;
  BookEntryDTO bookInfo;
  PersonDTO owner;
}
