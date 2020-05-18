package com.tartu.library.person.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    String name;
    String email;

    @CreationTimestamp
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

    public static Person of(PersonDTO personDTO) {
        Person p = new Person();
        p.setName(personDTO.getName());
        p.setEmail(personDTO.getEmail());
        return p;
    }
}
