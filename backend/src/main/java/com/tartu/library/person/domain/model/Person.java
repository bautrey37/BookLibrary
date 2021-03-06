package com.tartu.library.person.domain.model;

import com.tartu.library.person.application.dto.PersonDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Person {
  @Column(unique = true)
  String name;

  String email;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @CreationTimestamp private LocalDateTime createDateTime;
  @UpdateTimestamp private LocalDateTime updatedDateTime;

  public static Person of(PersonDTO personDTO) {
    return of(personDTO.getName(), personDTO.getEmail());
  }

  public static Person of(String name, String email) {
    Person p = new Person();
    p.setName(name);
    p.setEmail(email);
    return p;
  }
}
