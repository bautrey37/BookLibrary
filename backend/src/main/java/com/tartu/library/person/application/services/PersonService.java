package com.tartu.library.person.application.services;

import com.tartu.library.book.application.services.BookService;
import com.tartu.library.common.application.exception.EntityNotFoundException;
import com.tartu.library.person.application.dto.PersonDTO;
import com.tartu.library.person.domain.model.Person;
import com.tartu.library.person.domain.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {
  Logger logger = LoggerFactory.getLogger(PersonService.class);

  @Autowired PersonRepository personRepository;

  @Autowired PersonAssembler personAssembler;

  public PersonDTO createPerson(PersonDTO personDTO) {
    Person person = Person.of(personDTO);
    if (personRepository.existsByName(person.getName())) {
      logger.info(String.format("Person already exists. Name: (%s)", person.getName()));
      person = personRepository.findByName(person.getName());
    }
    else {
      personRepository.save(person);
    }
    return personAssembler.toModel(person);
  }

  public CollectionModel<PersonDTO> retrieveAllPersonDTO() {
    List<Person> persons = personRepository.findAll();
    return personAssembler.toCollectionModel(persons);
  }

  public PersonDTO retrievePersonDTO(UUID uuid) {
    Person person = retrievePerson(uuid);
    return personAssembler.toModel(person);
  }

  private Person retrievePerson(UUID uuid) {
    return personRepository
        .findById(uuid)
        .orElseThrow(() -> new EntityNotFoundException(Person.class, "uuid", uuid.toString()));
  }
}
