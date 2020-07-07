package com.tartu.library.book.domain.model;

import com.tartu.library.book.application.dto.BookEntryDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BookEntry {
  // International Standard Book Number
  String ISBN;

  @Column(unique = true)
  String bookName;

  String author;
  LocalDate publishDate;
  Integer numberOfBookItems;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @CreationTimestamp private LocalDateTime createDateTime;
  @UpdateTimestamp private LocalDateTime updatedDateTime;

  public static BookEntry of(String bookName, String author, LocalDate publishDate) {
    BookEntry be = new BookEntry();
    be.setBookName(bookName);
    be.setAuthor(author);
    be.setPublishDate(publishDate);
    be.setNumberOfBookItems(1);
    return be;
  }

  public static BookEntry of(BookEntryDTO bookEntryDTO) {
    BookEntry be = new BookEntry();
    be.setBookName(bookEntryDTO.getBookName());
    be.setAuthor(bookEntryDTO.getAuthor());
    be.setPublishDate(bookEntryDTO.getPublishDate());
    be.setNumberOfBookItems(1);
    return be;
  }

  public BookEntry incrementBookItemsCount() {
    this.numberOfBookItems = this.numberOfBookItems + 1;
    return this;
  }

  public BookEntry decrementBookItemsCount() {
    this.numberOfBookItems = this.numberOfBookItems - 1;
    return this;
  }
}
