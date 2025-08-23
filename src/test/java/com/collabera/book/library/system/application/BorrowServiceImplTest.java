package com.collabera.book.library.system.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.collabera.book.library.system.application.exceptions.BorrowBookFailedException;
import com.collabera.book.library.system.application.exceptions.ReturnBookFailedException;
import com.collabera.book.library.system.domain.exceptions.BorrowStateException;
import com.collabera.book.library.system.domain.model.Book;
import com.collabera.book.library.system.domain.model.BorrowRecord;
import com.collabera.book.library.system.domain.model.Borrower;
import com.collabera.book.library.system.domain.repositories.BookRepository;
import com.collabera.book.library.system.domain.repositories.BorrowRecordRepository;
import com.collabera.book.library.system.domain.repositories.BorrowerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

class BorrowServiceImplTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BorrowerRepository borrowerRepository;

  @Mock
  private BorrowRecordRepository borrowRecordRepository;

  @InjectMocks
  private BorrowServiceImpl borrowService;

  private UUID bookId;
  private UUID borrowerId;
  private UUID borrowRecordId;
  private Book book;
  private Borrower borrower;
  private BorrowRecord borrowRecord;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    bookId = UUID.randomUUID();
    borrowerId = UUID.randomUUID();
    borrowRecordId = UUID.randomUUID();

    book = Book.builder()
        .id(bookId)
        .isbn("123-ABC")
        .title("Test Book")
        .author("Author A")
        .build();

    borrower = Borrower.builder()
        .id(borrowerId)
        .name("Alice")
        .email("alice@example.com")
        .build();

    borrowRecord = BorrowRecord.createBorrow(book, borrower, "Borrowed by Alice");
    borrowRecord.setRecordId(borrowRecordId);
  }

  @Test
  void borrowBook_WhenBookAvailable_ShouldReturnBorrowRecord() {
    when(borrowRecordRepository.existsByBookIdAndActiveTrue(bookId)).thenReturn(false);
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
    when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

    BorrowRecord result = borrowService.borrowBook(borrowerId, bookId, "Borrowed by Alice");

    assertNotNull(result);
    assertEquals(borrowRecordId, result.getRecordId());
    verify(borrowRecordRepository).save(any(BorrowRecord.class));
  }

  @Test
  void borrowBook_WhenBookAlreadyBorrowed_ShouldThrowException() {
    when(borrowRecordRepository.existsByBookIdAndActiveTrue(bookId)).thenReturn(true);

    assertThrows(BorrowBookFailedException.class, () ->
        borrowService.borrowBook(borrowerId, bookId, "Borrowed by Alice"));

    verify(borrowRecordRepository, never()).save(any());
  }

  @Test
  void borrowBook_WhenBookNotFound_ShouldThrowException() {
    when(borrowRecordRepository.existsByBookIdAndActiveTrue(bookId)).thenReturn(false);
    when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

    assertThrows(BorrowBookFailedException.class, () ->
        borrowService.borrowBook(borrowerId, bookId, "Borrowed by Alice"));
  }

  @Test
  void borrowBook_WhenBorrowerNotFound_ShouldThrowException() {
    when(borrowRecordRepository.existsByBookIdAndActiveTrue(bookId)).thenReturn(false);
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.empty());

    assertThrows(BorrowBookFailedException.class, () ->
        borrowService.borrowBook(borrowerId, bookId, "Borrowed by Alice"));
  }

 @Test
  void returnBook_WhenValid_ShouldReturnBorrowRecord() throws BorrowStateException {
   when(borrowRecordRepository.findById(borrowRecordId)).thenReturn(Optional.of(borrowRecord));
   when(borrowRecordRepository.save(borrowRecord)).thenReturn(borrowRecord);

   try (MockedStatic<BorrowRecord> mocked = mockStatic(BorrowRecord.class)) {
     mocked.when(() -> BorrowRecord.createReturn(borrowRecord)).thenAnswer(invocation -> null);

     BorrowRecord result = borrowService.returnBook(borrowRecordId);

     assertNotNull(result);
     verify(borrowRecordRepository).save(borrowRecord);
   }
  }

  @Test
  void returnBook_WhenBorrowRecordNotFound_ShouldThrowException() {
    when(borrowRecordRepository.findById(borrowRecordId)).thenReturn(Optional.empty());

    assertThrows(ReturnBookFailedException.class, () ->
        borrowService.returnBook(borrowRecordId));
  }

  @Test
  void getCurrentBorrowRecords_ShouldReturnActiveRecords() {
    when(borrowRecordRepository.findByActiveIsTrue()).thenReturn(List.of(borrowRecord));

    List<BorrowRecord> result = borrowService.getCurrentBorrowRecords();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(borrowRecord, result.get(0));
    verify(borrowRecordRepository).findByActiveIsTrue();
  }
}
