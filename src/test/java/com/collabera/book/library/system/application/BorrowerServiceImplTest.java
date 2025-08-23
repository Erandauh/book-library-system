package com.collabera.book.library.system.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.collabera.book.library.system.domain.model.Borrower;
import com.collabera.book.library.system.domain.repositories.BorrowerRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BorrowerServiceImplTest {

  @Mock
  private BorrowerRepository borrowerRepository;

  @InjectMocks
  private BorrowerServiceImpl borrowerService;

  private Borrower borrower1;
  private Borrower borrower2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    borrower1 = Borrower.builder()
        .id(UUID.randomUUID())
        .name("Eric")
        .email("eramda@example.com")
        .build();

    borrower2 = Borrower.builder()
        .id(UUID.randomUUID())
        .name("Bob")
        .email("enu@example.com")
        .build();
  }

  @Test
  void registerBorrower_ShouldReturnSavedBorrower() {
    when(borrowerRepository.save(borrower1)).thenReturn(borrower1);

    Borrower saved = borrowerService.registerBorrower(borrower1);

    assertNotNull(saved);
    assertEquals("Eric", saved.getName());
    verify(borrowerRepository).save(borrower1);
  }

  @Test
  void getAllBorrowers_ShouldReturnListOfBorrowers() {
    when(borrowerRepository.findAll()).thenReturn(Arrays.asList(borrower1, borrower2));

    List<Borrower> borrowers = borrowerService.getAllBorrowers();

    assertNotNull(borrowers);
    assertEquals(2, borrowers.size());
    assertTrue(borrowers.contains(borrower1));
    assertTrue(borrowers.contains(borrower2));
    verify(borrowerRepository).findAll();
  }

  @Test
  void getAllBorrowers_WhenNoBorrowers_ShouldReturnEmptyList() {
    when(borrowerRepository.findAll()).thenReturn(List.of());

    List<Borrower> borrowers = borrowerService.getAllBorrowers();

    assertNotNull(borrowers);
    assertTrue(borrowers.isEmpty());
    verify(borrowerRepository).findAll();
  }
}
