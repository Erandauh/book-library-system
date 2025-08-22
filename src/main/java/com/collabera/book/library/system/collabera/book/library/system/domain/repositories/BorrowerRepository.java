package com.collabera.book.library.system.collabera.book.library.system.domain.repositories;

import com.collabera.book.library.system.collabera.book.library.system.domain.model.Borrower;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, UUID> {
}
