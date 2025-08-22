package com.collabera.book.library.system.collabera.book.library.system.application;

import com.collabera.book.library.system.collabera.book.library.system.domain.model.Borrower;
import java.util.List;

public interface BorrowerService {

  Borrower registerBorrower(Borrower borrower);

  List<Borrower> getAllBorrowers();
}
