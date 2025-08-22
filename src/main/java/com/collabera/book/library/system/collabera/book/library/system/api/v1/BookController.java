package com.collabera.book.library.system.collabera.book.library.system.api.v1;

import com.collabera.book.library.system.collabera.book.library.system.api.ro.request.BookRequest;
import com.collabera.book.library.system.collabera.book.library.system.api.ro.response.BookResponse;
import com.collabera.book.library.system.collabera.book.library.system.application.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @PostMapping("/library/book")
  public ResponseEntity<BookResponse> registerBook(@Valid @RequestBody BookRequest bookRequest) {

    // Controller receives DTO -> validates -> maps to Entity
    var book = bookRequest.toEntity();

    // passes Entity to Service -> Service applies business rules -> returns Entity
    var bookSaved = bookService.registerBook(book);

    // maps Entity back to DTO -> passes back as HTTP response
    return ResponseEntity.ok(BookResponse.of(bookSaved));
  }

  @GetMapping("/library/book")
  public ResponseEntity<List<BookResponse>> listBooks() {

    return ResponseEntity.ok(bookService.getAllBooks().stream()
        .map(BookResponse::of)
        .toList());
  }
}
