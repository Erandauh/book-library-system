package com.collabera.book.library.system.domain.repositories;

import com.collabera.book.library.system.domain.model.Book;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {

  Optional<Book> findFirstByIsbn(String isbn);
  Book save(Book book);
  Optional<Book> findById(UUID id);
  List<Book> findAll();
}
