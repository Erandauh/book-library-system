package com.collabera.book.library.system.collabera.book.library.system.domain.model;

import com.collabera.book.library.system.collabera.book.library.system.domain.exceptions.BookStateException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BOOK")
public class Book {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "BOOK_ID", updatable = false, nullable = false, columnDefinition = "UUID", unique = true)
  private UUID id;

  @Column(name = "ISBN")
  private String isbn;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "AUTHOR")
  private String author;

  @OneToMany(mappedBy = "book")
  private List<BorrowRecord> borrowRecords = new ArrayList<>();

  /*
   * Validate same isbn exsistance
   */
  public void validateConsistencyWith(Book other) throws BookStateException {
    // same ISBN => must have same title and author
    if (!this.getTitle().equals(other.getTitle()) ||
        !this.getAuthor().equals(other.getAuthor())) {
      throw new BookStateException(
          String.format(
              "Invalid book registration: ISBN %s already exists with different title/author.",
              other.getIsbn()));
    }
  }
}