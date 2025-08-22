package com.collabera.book.library.system.collabera.book.library.system.application;

import com.collabera.book.library.system.collabera.book.library.system.domain.model.Borrower;
import com.collabera.book.library.system.collabera.book.library.system.domain.repositories.BorrowerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowerServiceImpl implements BorrowerService {

  private final BorrowerRepository borrowerRepository;

  @Override
  public Borrower registerBorrower(Borrower borrower) {
    return borrowerRepository.save(borrower);
  }

  @Override
  public List<Borrower> getAllBorrowers() {
    return borrowerRepository.findAll();
  }
}
