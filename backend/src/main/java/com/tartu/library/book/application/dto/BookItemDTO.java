package com.tartu.library.book.application.dto;

import com.tartu.library.person.domain.model.PersonDTO;
import lombok.Data;

@Data
public class BookItemDTO {
    String serialNumber;
    BookEntryDTO bookInfo;
    PersonDTO owner;
}
