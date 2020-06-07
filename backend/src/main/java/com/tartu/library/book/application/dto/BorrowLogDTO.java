package com.tartu.library.book.application.dto;

import com.tartu.library.book.domain.model.BookStatus;
import com.tartu.library.person.application.dto.PersonDTO;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class BorrowLogDTO extends RepresentationModel<BorrowLogDTO> {
  //  BookItemDTO item;
  PersonDTO borrower;
  BookStatus status;
  LocalDateTime creationDateTime;
}
