package com.collabera.book.library.system.infrastructure;

import com.collabera.book.library.system.domain.model.Book;
import com.collabera.book.library.system.domain.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JPA adapter
interface JpaDataBookRepository extends JpaRepository<Book, UUID> {
  Optional<Book> findFirstByIsbn(String isbn);
}

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

  private final JpaDataBookRepository springDataRepo;

  @Override
  public Optional<Book> findFirstByIsbn(String isbn) {
    return springDataRepo.findFirstByIsbn(isbn);
  }

  @Override
  public Book save(Book book) {
    return springDataRepo.save(book);
  }

  @Override
  public Optional<Book> findById(UUID id) {
    return springDataRepo.findById(id);
  }

  @Override
  public List<Book> findAll() {
    return springDataRepo.findAll();
  }

}

