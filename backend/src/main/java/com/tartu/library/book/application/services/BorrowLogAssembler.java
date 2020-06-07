package com.tartu.library.book.application.services;

import com.tartu.library.book.application.dto.BorrowLogDTO;
import com.tartu.library.book.domain.model.BorrowLog;
import com.tartu.library.book.rest.BookItemRestController;
import com.tartu.library.person.application.services.PersonAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class BorrowLogAssembler
        extends RepresentationModelAssemblerSupport<BorrowLog, BorrowLogDTO> {

  public BorrowLogAssembler() {
    super(BookItemRestController.class, BorrowLogDTO.class);
  }

  @Autowired
  BookItemAssembler bookItemAssembler;

  @Autowired
  PersonAssembler personAssembler;

  @Override
  public BorrowLogDTO toModel(BorrowLog entity) {
    BorrowLogDTO dto = new BorrowLogDTO();
//    dto.setItem(bookItemAssembler.toModel(entity.getItem()));
    if (entity.getBorrower() != null) {
      dto.setBorrower(personAssembler.toModel(entity.getBorrower()));
    }
    dto.setStatus(entity.getStatus());
    dto.setCreationDateTime(entity.getCreateDateTime());
    return dto;
  }
}
