package com.generated.dao;

import com.generated.bean.Book;
import java.lang.String;
import java.util.List;

public interface BookDAO {
  List<Book> getAllBooks();

  List<Book> getBooksByRussell(String author);
}
