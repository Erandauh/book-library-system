package com.collabera.book.library.system.collabera.book.library.system.api.ro.response;

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
}
