package com.collabera.book.library.system.collabera.book.library.system.api.v1;

import com.collabera.book.library.system.collabera.book.library.system.api.ro.request.BorrowerRequest;
import com.collabera.book.library.system.collabera.book.library.system.api.ro.response.BorrowerResponse;
import com.collabera.book.library.system.collabera.book.library.system.application.BorrowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BorrowerController {

  private final BorrowerService borrowerService;

  @PostMapping("/library/borrower")
  public ResponseEntity<BorrowerResponse> registerBorrower(
      @Valid @RequestBody BorrowerRequest borrowerRequest) {

    // Controller receives DTO -> validates -> maps to Entity
    var borrower = borrowerRequest.toEntity();

    // passes Entity to Service -> Service applies business rules -> returns Entity
    var borrowerSaved = borrowerService.registerBorrower(borrower);

    // maps Entity back to DTO -> passes back as HTTP response
    return ResponseEntity.ok(BorrowerResponse.of(borrowerSaved));
  }

}
