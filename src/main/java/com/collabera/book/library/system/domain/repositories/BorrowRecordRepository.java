package com.collabera.book.library.system.domain.repositories;

import com.collabera.book.library.system.domain.model.BorrowRecord;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BorrowRecordRepository {

  BorrowRecord save(BorrowRecord borrowRecord);
  Optional<BorrowRecord> findById(UUID id);
  boolean existsByBookIdAndActiveTrue(UUID bookId);
  List<BorrowRecord> findByActiveIsTrue();
}