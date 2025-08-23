package com.collabera.book.library.system.collabera.book.library.system.application;

import com.collabera.book.library.system.collabera.book.library.system.application.exceptions.BookRegistrationFailureException;
import com.collabera.book.library.system.collabera.book.library.system.domain.exceptions.BookStateException;
import com.collabera.book.library.system.collabera.book.library.system.domain.model.Book;
import com.collabera.book.library.system.collabera.book.library.system.domain.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Override
  public Book registerBook(Book book) {
    // Orchestration of the book registration
    Optional<Book> existingBookOpt = bookRepository.findFirstByIsbn(book.getIsbn());

    if (existingBookOpt.isPresent()) {
      Book existingBook = existingBookOpt.get();
      try {
        existingBook.validateConsistencyWith(book);
      } catch (
          BookStateException e) {
        throw new BookRegistrationFailureException("Failed to register book", e);
      }
    }

    return bookRepository.save(book);
  }

  @Override
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }
}
