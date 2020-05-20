package com.tartu.library.book.application.dto;

import com.tartu.library.person.application.dto.PersonDTO;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class BookItemDTO extends RepresentationModel<BookItemDTO> {
    String serialNumber;
    BookEntryDTO bookInfo;
    PersonDTO owner;
}
