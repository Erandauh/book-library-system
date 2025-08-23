package com.collabera.book.library.system.api.ro.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BorrowRequest {

  @NotNull
  private UUID borrowerId;

  @NotNull
  private UUID bookId;

  @NotNull
  private String borrowMsg;
}