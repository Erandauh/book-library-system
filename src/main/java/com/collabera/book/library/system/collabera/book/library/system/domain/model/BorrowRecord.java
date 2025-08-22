package com.collabera.book.library.system.collabera.book.library.system.domain.model;

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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BORROW_RECORD")
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

  @Column(name = "ACTIVE" , nullable = false)
  private boolean active; // true if book is currently borrowed

  @Column(name = "BORROWED_AT")
  private LocalDateTime borrowedAt;

  @Column(name = "RETURNED_AT")
  private LocalDateTime returnedAt;
}
