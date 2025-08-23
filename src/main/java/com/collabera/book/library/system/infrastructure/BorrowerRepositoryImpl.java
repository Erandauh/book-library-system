package com.collabera.book.library.system.infrastructure;

import com.collabera.book.library.system.domain.model.Borrower;
import com.collabera.book.library.system.domain.repositories.BorrowerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JPA adapter
interface JpaDataBorrowerRepository extends JpaRepository<Borrower, UUID> {
}

@Repository
@RequiredArgsConstructor
public class BorrowerRepositoryImpl implements BorrowerRepository {

  private final JpaDataBorrowerRepository jpaDataBorrowerRepository;

  @Override
  public Borrower save(Borrower borrower) {
    return jpaDataBorrowerRepository.save(borrower);
  }

  @Override
  public List<Borrower> findAll() {
    return jpaDataBorrowerRepository.findAll();
  }

  @Override
  public Optional<Borrower> findById(UUID borrowerId) {
    return jpaDataBorrowerRepository.findById(borrowerId);
  }
}
