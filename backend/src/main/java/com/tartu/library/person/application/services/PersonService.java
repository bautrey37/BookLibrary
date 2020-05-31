package com.tartu.library.person.application.services;

import com.tartu.library.common.application.exception.EntityNotFoundException;
import com.tartu.library.person.application.dto.PersonDTO;
import com.tartu.library.person.domain.model.Person;
import com.tartu.library.person.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

  @Autowired PersonRepository personRepository;

  @Autowired PersonAssembler personAssembler;

  public PersonDTO createPerson(PersonDTO personDTO) {
    Person person = Person.of(personDTO);
    personRepository.save(person);
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
