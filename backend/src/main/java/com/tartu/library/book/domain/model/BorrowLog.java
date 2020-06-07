package com.tartu.library.book.domain.model;

import com.tartu.library.person.domain.model.Person;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class BorrowLog {
  @OneToOne
  BookItem item;
  @OneToOne
  Person borrower;
  @CreationTimestamp
  private LocalDateTime createDateTime;
  BookStatus status;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  public static BorrowLog of(BookItem item, Person borrower, BookStatus status) {
    BorrowLog log = new BorrowLog();
    log.setItem(item);
    log.setBorrower(borrower);
    log.setStatus(status);
    return log;
  }
}
