package com.collabera.book.library.system.collabera.book.library.system.api.v1;

import com.collabera.book.library.system.collabera.book.library.system.api.ro.request.BorrowRequest;
import com.collabera.book.library.system.collabera.book.library.system.api.ro.response.BorrowRecordResponse;
import com.collabera.book.library.system.collabera.book.library.system.api.ro.response.BorrowResponse;
import com.collabera.book.library.system.collabera.book.library.system.application.BorrowService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BorrowController {

  private final BorrowService borrowService;

  @PostMapping("/library/borrow")
  public ResponseEntity<BorrowResponse> borrowBook(@Valid @RequestBody BorrowRequest request) {

    var borrowRecord = borrowService.borrowBook(request.getBorrowerId(), request.getBookId(),
        request.getBorrowMsg());

    return ResponseEntity.ok(BorrowResponse.of(
        borrowRecord
    ));
  }

  @PostMapping("/library/{borrowRecordId}/return")
  public ResponseEntity<BorrowResponse> returnBook(@PathVariable UUID borrowRecordId) {

    var borrowRecord = borrowService.returnBook(borrowRecordId);

    return ResponseEntity.ok(BorrowResponse.of(borrowRecord));
  }

  @GetMapping("/library/current")
  public ResponseEntity<List<BorrowRecordResponse>> getCurrentBorrowRecords() {

    return ResponseEntity.ok(
        borrowService.getCurrentBorrowRecords().stream().map(BorrowRecordResponse::of).toList());
  }

}
