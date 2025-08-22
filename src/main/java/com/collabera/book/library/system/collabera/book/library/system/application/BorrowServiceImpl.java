package com.collabera.book.library.system.collabera.book.library.system.application;

import com.collabera.book.library.system.collabera.book.library.system.domain.model.Book;
import com.collabera.book.library.system.collabera.book.library.system.domain.model.BorrowRecord;
import com.collabera.book.library.system.collabera.book.library.system.domain.model.Borrower;
import com.collabera.book.library.system.collabera.book.library.system.domain.repositories.BookRepository;
import com.collabera.book.library.system.collabera.book.library.system.domain.repositories.BorrowRecordRepository;
import com.collabera.book.library.system.collabera.book.library.system.domain.repositories.BorrowerRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

  private final BookRepository bookRepository;
  private final BorrowerRepository borrowerRepository;
  private final BorrowRecordRepository borrowRecordRepository;

  @Override
  @Transactional
  public BorrowRecord  borrowBook(UUID borrowerId, UUID bookId, String message) {
  // check if book already borrowed
    if (borrowRecordRepository.existsByBookIdAndActiveTrue(bookId)) {
      throw new IllegalStateException("Book is already borrowed");
    }

    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new IllegalArgumentException("Book not found"));

    Borrower borrower = borrowerRepository.findById(borrowerId)
        .orElseThrow(() -> new IllegalArgumentException("Borrower not found"));

    BorrowRecord borrowRecord = BorrowRecord.builder()
        .book(book)
        .borrower(borrower)
        .borrowMsg(message)
        .borrowedAt(LocalDateTime.now())
        .active(true)
        .build();

    return borrowRecordRepository.save(borrowRecord);
  }

  @Transactional
  public BorrowRecord returnBook(UUID borrowRecordId) {
    BorrowRecord borrowRecord = borrowRecordRepository.findById(borrowRecordId)
        .orElseThrow(() -> new IllegalArgumentException("Borrow record not found"));

    if (!borrowRecord.isActive()) {
      throw new IllegalStateException("Book already returned");
    }

    borrowRecord.setActive(false);
    borrowRecord.setReturnedAt(LocalDateTime.now());

    return borrowRecordRepository.save(borrowRecord);
  }
}
