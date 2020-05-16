package com.tartu.library.book.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookEntryDTO {
    String ISBN;
    String bookName;
    String author;
    LocalDate publishDate;
}
