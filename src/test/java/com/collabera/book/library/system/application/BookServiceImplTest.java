package com.collabera.book.library.system.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.collabera.book.library.system.application.exceptions.BookRegistrationFailureException;
import com.collabera.book.library.system.domain.model.Book;
import com.collabera.book.library.system.domain.repositories.BookRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookServiceImplTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private BookServiceImpl bookService;

  private Book newBook;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    newBook = Book.builder()
        .id(UUID.randomUUID())
        .isbn("123-ABC")
        .title("Domain-Driven Design")
        .author("Eric Evans")
        .build();
  }

  @Test
  void registerBook_WhenIsbnNotExists_ShouldSaveBook() {

    when(bookRepository.findFirstByIsbn(newBook.getIsbn())).thenReturn(Optional.empty());
    when(bookRepository.save(newBook)).thenReturn(newBook);

    Book savedBook = bookService.registerBook(newBook);

    assertNotNull(savedBook);
    assertEquals("123-ABC", savedBook.getIsbn());
    verify(bookRepository).save(newBook);
  }

  @Test
  void registerBook_WhenIsbnExistsAndConsistent_ShouldSaveNewCopy() {

    Book consistentExistingBook = Book.builder()
        .id(UUID.randomUUID())
        .isbn(newBook.getIsbn())
        .title(newBook.getTitle())   // same title
        .author(newBook.getAuthor()) // same author
        .build();

    when(bookRepository.findFirstByIsbn(newBook.getIsbn())).thenReturn(
        Optional.of(consistentExistingBook));
    when(bookRepository.save(newBook)).thenReturn(newBook);

    Book savedBook = bookService.registerBook(newBook);

    assertNotNull(savedBook);
    verify(bookRepository).save(newBook);
  }


  @Test
  void registerBook_WhenIsbnExistsAndInconsistent_ShouldThrowBookRegistrationFailureException() {

    Book inconsistentBook = Book.builder()
        .id(UUID.randomUUID())
        .isbn(newBook.getIsbn())
        .title("Different Title") // different to force inconsistency
        .author("Different Author")
        .build();

    when(bookRepository.findFirstByIsbn(newBook.getIsbn())).thenReturn(
        Optional.of(inconsistentBook));

    assertThrows(BookRegistrationFailureException.class, () -> bookService.registerBook(newBook));
    verify(bookRepository, never()).save(any());
  }

  @Test
  void getAllBooks_ShouldReturnListOfBooks() {

    Book book1 = Book.builder()
        .id(UUID.randomUUID())
        .isbn("111-AAA")
        .title("Book One")
        .author("Author A")
        .build();

    Book book2 = Book.builder()
        .id(UUID.randomUUID())
        .isbn("222-BBB")
        .title("Book Two")
        .author("Author B")
        .build();

    when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

    List<Book> books = bookService.getAllBooks();

    assertNotNull(books);
    assertEquals(2, books.size());
    assertTrue(books.contains(book1));
    assertTrue(books.contains(book2));

    verify(bookRepository).findAll();
  }
}
