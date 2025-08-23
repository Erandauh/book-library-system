package com.collabera.book.library.system.domain.repositories;

import com.collabera.book.library.system.domain.model.BorrowRecord;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, UUID> {
  boolean existsByBookIdAndActiveTrue(UUID bookId);

  List<BorrowRecord> findByActiveIsTrue();
}