package com.collabera.book.library.system.api.ro.response;

import com.collabera.book.library.system.domain.model.Book;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {

  private UUID bookId;
  private String isbn;
  private String title;
  private String author;

  public static BookResponse of(Book book) {
    return BookResponse.builder().bookId(book.getId())
        .isbn(book.getIsbn())
        .title(book.getTitle())
        .author(book.getAuthor())
        .build();
  }
}
