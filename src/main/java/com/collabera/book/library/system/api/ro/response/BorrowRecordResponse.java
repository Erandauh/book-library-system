package com.collabera.book.library.system.api.ro.response;

import com.collabera.book.library.system.domain.model.BorrowRecord;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BorrowRecordResponse {
  private UUID borrowId;
  private UUID bookId;
  private String bookTitle;
  private String isbn;
  private UUID borrowerId;
  private String borrowerName;
  private LocalDateTime borrowedAt;

  public static BorrowRecordResponse of(BorrowRecord record) {
    return BorrowRecordResponse.builder()
        .borrowId(record.getRecordId())
        .bookId(record.getBook().getId())
        .bookTitle(record.getBook().getTitle())
        .isbn(record.getBook().getIsbn())
        .borrowerId(record.getBorrower().getId())
        .borrowerName(record.getBorrower().getName())
        .borrowedAt(record.getBorrowedAt())
        .build();
  }
}
