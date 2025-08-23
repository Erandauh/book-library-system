package com.collabera.book.library.system.application;

import com.collabera.book.library.system.domain.model.Book;
import java.util.List;

public interface BookService {

  Book registerBook(Book book);

  List<Book> getAllBooks();

}
