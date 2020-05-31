package com.tartu.library.book.application.dto;

import com.tartu.library.person.application.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class BookItemDTO extends RepresentationModel<BookItemDTO> {
  String serialNumber;
  @NotNull BookEntryDTO bookInfo;
  PersonDTO owner;
}
