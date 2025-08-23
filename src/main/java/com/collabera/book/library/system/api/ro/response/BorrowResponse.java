package com.collabera.book.library.system.api.ro.response;

import com.collabera.book.library.system.domain.model.BorrowRecord;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BorrowResponse {

  private UUID recordId;
  private UUID borrowerId;
  private UUID bookId;
  private String message;
  private boolean hasBorrowed;

  public static BorrowResponse of(BorrowRecord borrowRecord) {
    return BorrowResponse.builder()
        .recordId(borrowRecord.getRecordId())
        .borrowerId(borrowRecord.getBorrower().getId())
        .bookId(borrowRecord.getBook().getId())
        .hasBorrowed(borrowRecord.isActive())
        .message(borrowRecord.getBorrowMsg())
        .build();
  }
}