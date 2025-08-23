package com.collabera.book.library.system.api.ro.request;

import com.collabera.book.library.system.domain.model.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequest {

  @NotBlank
  private String isbn;

  @NotBlank
  private String title;

  @NotBlank
  private String author;

  public Book toEntity() {
    return Book.builder()
        .title(this.title)
        .author(this.author)
        .isbn(this.isbn)
        .build();
  }
}