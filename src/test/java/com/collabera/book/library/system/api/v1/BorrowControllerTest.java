package com.collabera.book.library.system.api.v1;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.collabera.book.library.system.api.ro.request.BorrowRequest;
import com.collabera.book.library.system.application.BorrowService;
import com.collabera.book.library.system.domain.model.Book;
import com.collabera.book.library.system.domain.model.BorrowRecord;
import com.collabera.book.library.system.domain.model.Borrower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class BorrowControllerTest {

  private MockMvc mockMvc;

  @Mock
  private BorrowService borrowService;

  @InjectMocks
  private BorrowController borrowController;

  private ObjectMapper objectMapper = new ObjectMapper();

  private BorrowRequest borrowRequest;
  private BorrowRecord borrowRecord;
  private Book book;
  private Borrower borrower;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(borrowController).build();

    UUID bookId = UUID.randomUUID();
    UUID borrowerId = UUID.randomUUID();
    UUID borrowRecordId = UUID.randomUUID();

    book = Book.builder()
        .id(bookId)
        .isbn("12345")
        .title("Effective Java")
        .author("Joshua Bloch")
        .build();

    borrower = Borrower.builder()
        .id(borrowerId)
        .name("John Doe")
        .email("john@example.com")
        .build();

    borrowRecord = BorrowRecord.builder()
        .recordId(borrowRecordId)
        .book(book)
        .borrower(borrower)
        .borrowMsg("Borrowed for testing")
        .borrowedAt(LocalDateTime.now())
        .active(true)
        .build();

    borrowRequest = BorrowRequest.builder()
        .bookId(bookId)
        .borrowerId(borrowerId)
        .borrowMsg("Borrowed for testing")
        .build();
  }

  @Test
  void borrowBook_ShouldReturnBorrowResponse() throws Exception {
    when(borrowService.borrowBook(any(UUID.class), any(UUID.class), anyString()))
        .thenReturn(borrowRecord);

    mockMvc.perform(post("/v1/library/borrow")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(borrowRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.recordId").value(borrowRecord.getRecordId().toString()))
        .andExpect(jsonPath("$.bookId").value(borrowRecord.getBook().getId().toString()))
        .andExpect(jsonPath("$.borrowerId").value(borrowRecord.getBorrower().getId().toString()));

    verify(borrowService).borrowBook(borrowRequest.getBorrowerId(), borrowRequest.getBookId(), borrowRequest.getBorrowMsg());
  }

  @Test
  void returnBook_ShouldReturnBorrowResponse() throws Exception {
    UUID borrowRecordId = borrowRecord.getRecordId();
    when(borrowService.returnBook(borrowRecordId)).thenReturn(borrowRecord);

    mockMvc.perform(post("/v1/library/" + borrowRecordId + "/return")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.recordId").value(borrowRecord.getRecordId().toString()));

    verify(borrowService).returnBook(borrowRecordId);
  }

  @Test
  void getCurrentBorrowRecords_ShouldReturnListOfBorrowRecords() throws Exception {
    when(borrowService.getCurrentBorrowRecords()).thenReturn(List.of(borrowRecord));

    mockMvc.perform(get("/v1/library/current")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].borrowId").value(borrowRecord.getRecordId().toString()));

    verify(borrowService).getCurrentBorrowRecords();
  }
}
