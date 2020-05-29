package com.tartu.library.person.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class PersonDTO extends RepresentationModel<PersonDTO> {
  String name;
  String email;
}
