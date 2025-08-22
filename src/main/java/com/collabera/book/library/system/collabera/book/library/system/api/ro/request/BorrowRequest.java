package com.collabera.book.library.system.collabera.book.library.system.api.ro.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class BorrowRequest {

  @NotNull
  private UUID borrowerId;

  @NotNull
  private UUID bookId;

  @NotNull
  private String borrowMsg;
}