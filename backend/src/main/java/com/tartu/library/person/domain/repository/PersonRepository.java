package com.tartu.library.person.domain.repository;

import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.person.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
  @Query(
      "select case when count(b)> 0 then true else false end from Person b where b.name like %?1%")
  boolean existsByName(String name);

  @Query("select b from Person b where b.name like %?1%")
  Person findByName(String name);
}
