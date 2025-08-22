package com.collabera.book.library.system.collabera.book.library.system.application;

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
    // Check if ISBN already exists
    Optional<Book> existingBookOpt = bookRepository.findFirstByIsbn(book.getIsbn());

    if (existingBookOpt.isPresent()) {
      Book existingBook = existingBookOpt.get();

      // same ISBN => must have same title and author
      if (!existingBook.getTitle().equals(book.getTitle()) ||
          !existingBook.getAuthor().equals(book.getAuthor())) {
        throw new IllegalArgumentException(
            String.format(
                "Invalid book registration: ISBN %s already exists with different title/author.",
                book.getIsbn()));
      }
    }

    // Always save new copy (new UUID)
    book.setBorrowed(false);

    return bookRepository.save(book);
  }

  @Override
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }
}
