package com.tartu.library.person.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class PersonDTO extends RepresentationModel<PersonDTO> {
  @NotNull String name;
  String email;
}
