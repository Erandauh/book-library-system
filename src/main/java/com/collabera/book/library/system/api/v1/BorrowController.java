package com.collabera.book.library.system.api.v1;

import com.collabera.book.library.system.api.ro.request.BorrowRequest;
import com.collabera.book.library.system.api.ro.response.BorrowRecordResponse;
import com.collabera.book.library.system.api.ro.response.BorrowResponse;
import com.collabera.book.library.system.application.BorrowService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BorrowController {

  private final BorrowService borrowService;

  @PostMapping("/library/borrow")
  public ResponseEntity<BorrowResponse> borrowBook(@Valid @RequestBody BorrowRequest request) {

    log.info("Received borrow request. BorrowerId={}, BookId={}, Msg={}",
        request.getBorrowerId(), request.getBookId(), request.getBorrowMsg());

    var borrowRecord = borrowService.borrowBook(
        request.getBorrowerId(),
        request.getBookId(),
        request.getBorrowMsg()
    );

    log.info("Book borrowed successfully. BorrowRecordId={}", borrowRecord.getRecordId());

    var response = BorrowResponse.of(borrowRecord);
    log.debug("Mapped BorrowRecord to BorrowResponse: {}", response);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/library/{borrowRecordId}/return")
  public ResponseEntity<BorrowResponse> returnBook(@PathVariable UUID borrowRecordId) {

    log.info("Received return request for BorrowRecordId={}", borrowRecordId);

    var borrowRecord = borrowService.returnBook(borrowRecordId);
    log.info("Book returned successfully. BorrowRecordId={}", borrowRecord.getRecordId());

    var response = BorrowResponse.of(borrowRecord);
    log.debug("Mapped BorrowRecord to BorrowResponse: {}", response);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/library/current")
  public ResponseEntity<List<BorrowRecordResponse>> getCurrentBorrowRecords() {

    log.info("Received request to fetch current borrow records");

    var records = borrowService.getCurrentBorrowRecords();
    log.debug("Fetched {} active borrow records", records.size());

    var responseList = records.stream()
        .map(BorrowRecordResponse::of)
        .toList();

    log.info("Returning {} borrow records in response", responseList.size());

    return ResponseEntity.ok(responseList);
  }

}
