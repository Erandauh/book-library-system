package com.collabera.book.library.system.collabera.book.library.system.domain.repositories;

import com.collabera.book.library.system.collabera.book.library.system.domain.model.Book;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, UUID> {

  Optional<Book> findFirstByIsbn(String isbn);
}
