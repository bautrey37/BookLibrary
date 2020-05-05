package com.tartu.library.book.domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

public class BookItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


}
