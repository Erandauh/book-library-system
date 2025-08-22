package com.collabera.book.library.system.collabera.book.library.system.application;

import com.collabera.book.library.system.collabera.book.library.system.domain.model.BorrowRecord;
import java.util.List;
import java.util.UUID;

public interface BorrowService {

  BorrowRecord borrowBook(UUID borrowerId, UUID bookId, String message);

  BorrowRecord returnBook(UUID borrowRecordId);

  List<BorrowRecord> getCurrentBorrowRecords();
}
