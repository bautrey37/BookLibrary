package com.tartu.library.book.domain.model;

import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.person.domain.model.Person;
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
public class BookItem {
  String serialNumber;

  @Enumerated(EnumType.STRING)
  BookStatus status;

  @OneToOne Person owner;
  @OneToOne Person borrower;
  @ManyToOne BookEntry bookInfo;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @CreationTimestamp private LocalDateTime createDateTime;
  @UpdateTimestamp private LocalDateTime updatedDateTime;

  public static BookItem of(BookEntry bookEntry, Person owner, String serialNumber) {
    BookItem bi = new BookItem();
    bi.setStatus(BookStatus.AVAILABLE);
    bi.setBookInfo(bookEntry);
    bi.setOwner(owner);
    bi.setSerialNumber(serialNumber);
    return bi;
  }

  public static BookItem of(BookItemDTO bookItemDTO) {
    BookEntry bookEntry = BookEntry.of(bookItemDTO.getBookInfo());
    Person owner = Person.of(bookItemDTO.getOwner());

    BookItem bi = new BookItem();
    bi.setStatus(BookStatus.AVAILABLE);
    bi.setBookInfo(bookEntry);
    bi.setOwner(owner);
    bi.setSerialNumber(bookItemDTO.getSerialNumber());
    return bi;
  }
}
