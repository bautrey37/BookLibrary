package com.tartu.library.person.application.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class PersonDTO extends RepresentationModel<PersonDTO> {
    String name;
    String email;
}