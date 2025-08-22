package com.collabera.book.library.system.collabera.book.library.system.api.ro.response;

import com.collabera.book.library.system.collabera.book.library.system.domain.model.Borrower;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerResponse {

  private UUID borrowerId;
  private String name;
  private String email;

  public static BorrowerResponse of(Borrower borrower) {
    return BorrowerResponse.builder()
        .borrowerId(borrower.getId())
        .name(borrower.getName())
        .email(borrower.getEmail())
        .build();
  }

}