package com.collabera.book.library.system.domain.repositories;

import com.collabera.book.library.system.domain.model.Borrower;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BorrowerRepository {

  Borrower save(Borrower borrower);

  List<Borrower> findAll();

  Optional<Borrower> findById(UUID borrowerId);
}
