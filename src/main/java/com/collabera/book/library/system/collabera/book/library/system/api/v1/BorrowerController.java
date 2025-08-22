package com.collabera.book.library.system.collabera.book.library.system.api.v1;

import com.collabera.book.library.system.collabera.book.library.system.application.BorrowerService;
import com.collabera.book.library.system.collabera.book.library.system.domain.model.Borrower;
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
  public ResponseEntity<Borrower> registerBorrower(@RequestBody Borrower borrower) {
    return ResponseEntity.ok(borrowerService.registerBorrower(borrower));
  }

}
