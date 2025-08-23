package com.collabera.book.library.system.api.v1;

import com.collabera.book.library.system.api.ro.request.BookRequest;
import com.collabera.book.library.system.api.ro.response.BookResponse;
import com.collabera.book.library.system.application.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
public class BookController {

  private final BookService bookService;

  @PostMapping("/library/book")
  public ResponseEntity<BookResponse> registerBook(@Valid @RequestBody BookRequest bookRequest) {

    // Controller receives DTO -> validates -> maps to Entity
    log.info("Received request to register a new book: {}", bookRequest);
    var book = bookRequest.toEntity();
    log.debug("Mapped BookRequest to Book entity: {}", book);

    // passes Entity to Service -> Service applies business rules -> returns Entity
    var bookSaved = bookService.registerBook(book);
    log.info("Book successfully registered with ID: {}", bookSaved.getId());

    // maps Entity back to DTO -> passes back as HTTP response
    var response = BookResponse.of(bookSaved);
    log.debug("Mapped Book entity to BookResponse: {}", response);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/library/book")
  public ResponseEntity<List<BookResponse>> listBooks() {
    log.info("Received request to list all books");

    var books = bookService.getAllBooks();
    log.debug("Fetched {} books from service", books.size());

    var responseList = books.stream()
        .map(BookResponse::of)
        .toList();

    log.info("Returning {} books in response", responseList.size());

    return ResponseEntity.ok(responseList);
  }
}
