package com.collabera.book.library.system.collabera.book.library.system.api.ro.request;

import com.collabera.book.library.system.collabera.book.library.system.domain.model.Borrower;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerRequest {

  @NotBlank(message = "Name is required")
  @Size(max = 255, message = "Name must not exceed 255 characters")
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  @Size(max = 255, message = "Email must not exceed 255 characters")
  private String email;

  public Borrower toEntity() {
    return Borrower.builder()
        .name(this.name)
        .email(this.email)
        .build();
  }

}