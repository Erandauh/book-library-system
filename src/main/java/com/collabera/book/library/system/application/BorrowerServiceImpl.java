package com.collabera.book.library.system.application;

import com.collabera.book.library.system.domain.model.Borrower;
import com.collabera.book.library.system.domain.repositories.BorrowerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowerServiceImpl implements BorrowerService {

  private final BorrowerRepository borrowerRepository;

  @Override
  public Borrower registerBorrower(Borrower borrower) {
    log.info("Attempting to register borrower with name={} and email={}",
        borrower.getName(), borrower.getEmail());

    Borrower savedBorrower = borrowerRepository.save(borrower);
    log.info("Borrower successfully registered with ID={}", savedBorrower.getId());

    return savedBorrower;
  }

  @Override
  public List<Borrower> getAllBorrowers() {
    log.info("Fetching all borrowers from repository");

    List<Borrower> borrowers = borrowerRepository.findAll();
    log.debug("Fetched {} borrowers", borrowers.size());

    return borrowers;
  }
}
