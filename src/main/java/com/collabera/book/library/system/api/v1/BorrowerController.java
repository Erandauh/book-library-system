package com.collabera.book.library.system.api.v1;

import com.collabera.book.library.system.api.ro.request.BorrowerRequest;
import com.collabera.book.library.system.api.ro.response.BorrowerResponse;
import com.collabera.book.library.system.application.BorrowerService;
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
public class BorrowerController {

  private final BorrowerService borrowerService;

  @PostMapping("/library/borrower")
  public ResponseEntity<BorrowerResponse> registerBorrower(
      @Valid @RequestBody BorrowerRequest borrowerRequest) {

    log.info("Received request to register a new borrower: {}", borrowerRequest);

    // Controller receives DTO -> validates -> maps to Entity
    var borrower = borrowerRequest.toEntity();
    log.debug("Mapped BorrowerRequest to Borrower entity: {}", borrower);

    // passes Entity to Service -> Service applies business rules -> returns Entity
    var borrowerSaved = borrowerService.registerBorrower(borrower);
    log.info("Borrower successfully registered with ID: {}", borrowerSaved.getId());

    // maps Entity back to DTO -> passes back as HTTP response
    var response = BorrowerResponse.of(borrowerSaved);
    log.debug("Mapped Borrower entity to BorrowerResponse: {}", response);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/library/borrower")
  public ResponseEntity<List<BorrowerResponse>> listBooks() {

    log.info("Received request to list all borrowers");

    var borrowers = borrowerService.getAllBorrowers();
    log.debug("Fetched {} borrowers from service", borrowers.size());

    var responseList = borrowers.stream()
        .map(BorrowerResponse::of)
        .toList();

    log.info("Returning {} borrowers in response", responseList.size());

    return ResponseEntity.ok(responseList);
  }
}
