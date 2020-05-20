package com.tartu.library.person.application.services;

import com.tartu.library.person.application.dto.PersonDTO;
import com.tartu.library.person.domain.model.Person;
import com.tartu.library.person.rest.PersonRestController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PersonAssembler extends RepresentationModelAssemblerSupport<Person, PersonDTO> {
  public PersonAssembler() {
    super(PersonRestController.class, PersonDTO.class);
  }

  @Override
  public PersonDTO toModel(Person person) {
    PersonDTO dto = createModelWithId(person.getId(), person);

    return dto;
  }
}
