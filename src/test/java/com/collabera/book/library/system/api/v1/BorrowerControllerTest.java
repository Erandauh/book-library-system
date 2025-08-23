package com.collabera.book.library.system.api.v1;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.collabera.book.library.system.api.ro.request.BorrowerRequest;
import com.collabera.book.library.system.application.BorrowerService;
import com.collabera.book.library.system.domain.model.Borrower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

class BorrowerControllerTest {

  private MockMvc mockMvc;

  @Mock
  private BorrowerService borrowerService;

  @InjectMocks
  private BorrowerController borrowerController;

  private ObjectMapper objectMapper = new ObjectMapper();

  private BorrowerRequest borrowerRequest;
  private Borrower borrower;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(borrowerController).build();

    UUID borrowerId = UUID.randomUUID();

    borrowerRequest = BorrowerRequest.builder()
        .name("John Doe")
        .email("john@example.com")
        .build();

    borrower = Borrower.builder()
        .id(borrowerId)
        .name(borrowerRequest.getName())
        .email(borrowerRequest.getEmail())
        .build();
  }

  @Test
  void registerBorrower_ShouldReturnBorrowerResponse() throws Exception {
    when(borrowerService.registerBorrower(any(Borrower.class))).thenReturn(borrower);

    mockMvc.perform(post("/v1/library/borrower")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(borrowerRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.borrowerId").value(borrower.getId().toString()))
        .andExpect(jsonPath("$.name").value(borrower.getName()))
        .andExpect(jsonPath("$.email").value(borrower.getEmail()));

    verify(borrowerService).registerBorrower(any(Borrower.class));
  }

  @Test
  void listBorrowers_ShouldReturnListOfBorrowerResponses() throws Exception {
    when(borrowerService.getAllBorrowers()).thenReturn(List.of(borrower));

    mockMvc.perform(get("/v1/library/borrower")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].borrowerId").value(borrower.getId().toString()))
        .andExpect(jsonPath("$[0].name").value(borrower.getName()))
        .andExpect(jsonPath("$[0].email").value(borrower.getEmail()));

    verify(borrowerService).getAllBorrowers();
  }
}
