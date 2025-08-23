package com.collabera.book.library.system.application;

import com.collabera.book.library.system.application.exceptions.BorrowBookFailedException;
import com.collabera.book.library.system.application.exceptions.ReturnBookFailedException;
import com.collabera.book.library.system.domain.exceptions.BorrowStateException;
import com.collabera.book.library.system.domain.model.Book;
import com.collabera.book.library.system.domain.model.BorrowRecord;
import com.collabera.book.library.system.domain.model.Borrower;
import com.collabera.book.library.system.domain.repositories.BookRepository;
import com.collabera.book.library.system.domain.repositories.BorrowRecordRepository;
import com.collabera.book.library.system.domain.repositories.BorrowerRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowServiceImpl implements BorrowService {

  private final BookRepository bookRepository;
  private final BorrowerRepository borrowerRepository;
  private final BorrowRecordRepository borrowRecordRepository;

  @Override
  @Transactional
  public BorrowRecord borrowBook(UUID borrowerId, UUID bookId, String message) {

    log.info("Borrow request received: borrowerId={}, bookId={}, message={}",
        borrowerId, bookId, message);

    // check if book already borrowed
    if (borrowRecordRepository.existsByBookIdAndActiveTrue(bookId)) {
      log.warn("Borrow failed: Book {} is already borrowed", bookId);
      throw new BorrowBookFailedException("Book is already borrowed");
    }

    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> {
          log.error("Borrow failed: Book {} not found", bookId);
          return new BorrowBookFailedException("Book not found");
        });

    Borrower borrower = borrowerRepository.findById(borrowerId)
        .orElseThrow(() -> {
          log.error("Borrow failed: Borrower {} not found", borrowerId);
          return new BorrowBookFailedException("Borrower not found");
        });

    BorrowRecord borrowRecord = BorrowRecord.createBorrow(book, borrower, message);
    BorrowRecord savedRecord = borrowRecordRepository.save(borrowRecord);

    log.info("Borrow successful: borrowRecordId={}, borrowerId={}, bookId={}",
        savedRecord.getRecordId(), borrowerId, bookId);

    return savedRecord;
  }

  @Transactional
  public BorrowRecord returnBook(UUID borrowRecordId) {
    log.info("Return request received: borrowRecordId={}", borrowRecordId);

    BorrowRecord borrowRecord = borrowRecordRepository.findById(borrowRecordId)
        .orElseThrow(() -> {
          log.error("Return failed: Borrow record {} not found", borrowRecordId);
          return new ReturnBookFailedException("Borrow record not found");
        });

    try {
      BorrowRecord.createReturn(borrowRecord);
    } catch (BorrowStateException e) {
      log.error("Return failed: invalid state for borrowRecordId={}", borrowRecordId, e);
      throw new ReturnBookFailedException("Failed to return book", e);
    }

    BorrowRecord savedRecord = borrowRecordRepository.save(borrowRecord);
    log.info("Return successful: borrowRecordId={}", savedRecord.getRecordId());

    return savedRecord;
  }

  public List<BorrowRecord> getCurrentBorrowRecords() {
    log.info("Fetching all currently active borrow records");

    List<BorrowRecord> activeRecords = borrowRecordRepository.findByActiveIsTrue();
    log.debug("Fetched {} active borrow records", activeRecords.size());

    return activeRecords;
  }
}
