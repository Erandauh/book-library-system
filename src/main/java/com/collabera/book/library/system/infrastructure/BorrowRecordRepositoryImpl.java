package com.collabera.book.library.system.infrastructure;

import com.collabera.book.library.system.domain.model.BorrowRecord;
import com.collabera.book.library.system.domain.repositories.BorrowRecordRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

interface JPABorrowRecordRepository extends JpaRepository<BorrowRecord, UUID> {
  boolean existsByBookIdAndActiveTrue(UUID bookId);

  List<BorrowRecord> findByActiveIsTrue();
}

@Repository
@RequiredArgsConstructor
public class BorrowRecordRepositoryImpl implements BorrowRecordRepository {

  private final JPABorrowRecordRepository jpaBorrowRecordRepository;

  @Override
  public boolean existsByBookIdAndActiveTrue(UUID bookId) {
    return jpaBorrowRecordRepository.existsByBookIdAndActiveTrue(bookId);
  }

  @Override
  public List<BorrowRecord> findByActiveIsTrue() {
    return jpaBorrowRecordRepository.findByActiveIsTrue();
  }

  @Override
  public BorrowRecord save(BorrowRecord borrowRecord) {
    return jpaBorrowRecordRepository.save(borrowRecord);
  }

  @Override
  public Optional<BorrowRecord> findById(UUID id) {
    return jpaBorrowRecordRepository.findById(id);
  }
}

