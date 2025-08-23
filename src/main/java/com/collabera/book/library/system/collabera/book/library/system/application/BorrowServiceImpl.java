package com.collabera.book.library.system.collabera.book.library.system.application;

import com.collabera.book.library.system.collabera.book.library.system.application.exceptions.BorrowBookFailedException;
import com.collabera.book.library.system.collabera.book.library.system.application.exceptions.ReturnBookFailedException;
import com.collabera.book.library.system.collabera.book.library.system.domain.exceptions.BorrowStateException;
import com.collabera.book.library.system.collabera.book.library.system.domain.model.Book;
import com.collabera.book.library.system.collabera.book.library.system.domain.model.BorrowRecord;
import com.collabera.book.library.system.collabera.book.library.system.domain.model.Borrower;
import com.collabera.book.library.system.collabera.book.library.system.domain.repositories.BookRepository;
import com.collabera.book.library.system.collabera.book.library.system.domain.repositories.BorrowRecordRepository;
import com.collabera.book.library.system.collabera.book.library.system.domain.repositories.BorrowerRepository;
import jakarta.transaction.Transactional;
import java.util.List;
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
  public BorrowRecord borrowBook(UUID borrowerId, UUID bookId, String message) {
    // check if book already borrowed
    if (borrowRecordRepository.existsByBookIdAndActiveTrue(bookId)) {
      throw new BorrowBookFailedException("Book is already borrowed");
    }

    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BorrowBookFailedException("Book not found"));

    Borrower borrower = borrowerRepository.findById(borrowerId)
        .orElseThrow(() -> new BorrowBookFailedException("Borrower not found"));

    BorrowRecord borrowRecord = BorrowRecord.createBorrow(book, borrower, message);

    return borrowRecordRepository.save(borrowRecord);
  }

  @Transactional
  public BorrowRecord returnBook(UUID borrowRecordId) {
    BorrowRecord borrowRecord = borrowRecordRepository.findById(borrowRecordId)
        .orElseThrow(() -> new ReturnBookFailedException("Borrow record not found"));

    try {
      BorrowRecord.createReturn(borrowRecord);
    } catch (BorrowStateException e) {
      // application layer exception wraps the domain exception
      throw new ReturnBookFailedException("Failed to return book", e);
    }

    return borrowRecordRepository.save(borrowRecord);
  }

  public List<BorrowRecord> getCurrentBorrowRecords() {
    return borrowRecordRepository.findByActiveIsTrue();
  }
}
