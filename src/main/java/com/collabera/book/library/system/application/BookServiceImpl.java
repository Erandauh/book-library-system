package com.collabera.book.library.system.application;

import com.collabera.book.library.system.application.exceptions.BookRegistrationFailureException;
import com.collabera.book.library.system.domain.exceptions.BookStateException;
import com.collabera.book.library.system.domain.model.Book;
import com.collabera.book.library.system.domain.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Override
  public Book registerBook(Book book) {
    log.info("Attempting to register book with ISBN={}", book.getIsbn());

    // Orchestration of the book registration
    Optional<Book> existingBookOpt = bookRepository.findFirstByIsbn(book.getIsbn());

    if (existingBookOpt.isPresent()) {
      Book existingBook = existingBookOpt.get();
      log.warn("Book with ISBN={} already exists. Validating consistency...", book.getIsbn());

      try {
        existingBook.validateConsistencyWith(book);
        log.info("Existing book with ISBN={} is consistent with new registration", book.getIsbn());
      } catch (BookStateException e) {
        log.error("Book state inconsistency detected for ISBN={}", book.getIsbn(), e);
        throw new BookRegistrationFailureException("Failed to register book", e);
      }
    }

    Book savedBook = bookRepository.save(book);
    log.info("Book successfully registered with ID={} and ISBN={}", savedBook.getId(), savedBook.getIsbn());

    return savedBook;
  }

  @Override
  public List<Book> getAllBooks() {
    log.info("Fetching all books from repository");

    List<Book> books = bookRepository.findAll();
    log.debug("Fetched {} books", books.size());

    return books;
  }
}
