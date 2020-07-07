package com.tartu.library.book.application.dto;

import com.tartu.library.book.domain.model.BookStatus;
import com.tartu.library.person.application.dto.PersonDTO;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class BookItemDTO extends RepresentationModel<BookItemDTO> {
  @NotNull String serialNumber;
  @NotNull BookEntryDTO bookInfo;
  UUID id;
  PersonDTO owner;
  PersonDTO borrower;
  BookStatus status;
}
