package com.collabera.book.library.system;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

class ApplicationMainMethodTest {

  @Test
  void testMainMethodCallsSpringApplicationRun() {
    try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
      ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
      mockedSpringApplication.when(() -> SpringApplication.run(eq(Application.class), any(String[].class)))
          .thenReturn(mockContext);

      String[] args = {"--spring.profiles.active=test"};
      Application.main(args);

      mockedSpringApplication.verify(() -> SpringApplication.run(Application.class, args));
    }
  }
}