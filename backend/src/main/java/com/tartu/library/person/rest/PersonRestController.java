package com.tartu.library.person.rest;

import com.tartu.library.person.application.dto.PersonDTO;
import com.tartu.library.person.application.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/person")
public class PersonRestController {
  Logger logger = LoggerFactory.getLogger(PersonRestController.class);

  @Autowired PersonService personService;

  @GetMapping
  public CollectionModel<PersonDTO> retrieveAllPersons() {
    logger.info("Retrieving all persons");
    return personService.retrieveAllPersonDTO();
  }

  @PostMapping
  public PersonDTO createPerson(@RequestBody @Validated PersonDTO personDTO) {
    logger.info("Creating Person");
    return personService.createPerson(personDTO);
  }

  @GetMapping("{uuid}")
  public PersonDTO retrievePerson(@PathVariable UUID uuid) {
    logger.info(String.format("Retrieving Person (%s)", uuid.toString()));
    return personService.retrievePersonDTO(uuid);
  }
}
