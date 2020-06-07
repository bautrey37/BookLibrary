package com.tartu.library.book.domain.model;

import com.tartu.library.person.domain.model.Person;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BorrowLog {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @OneToOne BookItem item;

  @OneToOne Person borrower;

  LocalDateTime borrowDate;
  LocalDateTime returnDate;

  public static BorrowLog of(BookItem item, Person borrower) {
    BorrowLog log = new BorrowLog();
    log.setItem(item);
    log.setBorrower(borrower);
    log.setBorrowDate(LocalDateTime.now());
    return log;
  }

  public static BorrowLog returnBook() {
    BorrowLog log = new BorrowLog();
    log.setReturnDate(LocalDateTime.now());
    return log;
  }
}
