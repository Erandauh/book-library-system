package com.collabera.book.library.system.domain.model;

import com.collabera.book.library.system.domain.exceptions.BorrowStateException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BORROW_RECORD")
@Slf4j
public class BorrowRecord {

  @Id
  @GeneratedValue
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "RECORD_ID", updatable = false, nullable = false, columnDefinition = "UUID")
  private UUID recordId;

  @ManyToOne
  @JoinColumn(name = "BOOK_ID")
  private Book book;

  @ManyToOne
  @JoinColumn(name = "BORROWER_ID")
  private Borrower borrower;

  @Column(name = "BORROW_MSG")
  private String borrowMsg;

  @Column(name = "ACTIVE", nullable = false)
  private boolean active; // true if book is currently borrowed

  @Column(name = "BORROWED_AT")
  private LocalDateTime borrowedAt;

  @Column(name = "RETURNED_AT")
  private LocalDateTime returnedAt;

  public static BorrowRecord createBorrow(Book book, Borrower borrower, String message) {
    if (book == null) {
      log.error("Failed to create borrow record: book is null");
      throw new BorrowStateException("Book cannot be null when creating a borrow record.");
    }
    if (borrower == null) {
      log.error("Failed to create borrow record: borrower is null");
      throw new BorrowStateException("Borrower cannot be null when creating a borrow record.");
    }

    log.info("Creating borrow record: bookId={}, borrowerId={}, message={}",
        book.getId(), borrower.getId(), message);

    BorrowRecord borrowRecord = BorrowRecord.builder()
        .book(book)
        .borrower(borrower)
        .borrowMsg(message)
        .borrowedAt(LocalDateTime.now())
        .active(true)
        .build();

    log.debug("Borrow record created successfully: {}", borrowRecord);

    return borrowRecord;
  }

  public static void createReturn(BorrowRecord returnRecord) throws BorrowStateException {

    if (!returnRecord.isActive()) {
      throw new BorrowStateException("Book already returned");
    }

    log.info("Returning borrow record: borrowRecordId={}, bookId={}, borrowerId={}",
        returnRecord.getRecordId(),
        returnRecord.getBook().getId(),
        returnRecord.getBorrower().getId());

    returnRecord.setActive(false);
    returnRecord.setReturnedAt(LocalDateTime.now());
    returnRecord.setBorrowMsg(
        returnRecord.getBorrowMsg() == null ? "Returned" : returnRecord.getBorrowMsg() + " - Returned");
  }
}
