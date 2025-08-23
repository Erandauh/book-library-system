package com.collabera.book.library.system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI libraryOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Collabera Library Management System")
            .description("REST API for managing books, borrowers, and borrow/return operations.")
            .contact(new Contact().name("Collabera").email("support@collabera.example")));
  }
}
