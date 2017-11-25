package com.generated.jdbc;

import com.generate.parce.bean.Substitution;
import com.generated.bean.BaseJdbcDAOGen;
import com.generated.bean.Book;
import com.generated.dao.BookDAO;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class JDBCBookDAO extends BaseJdbcDAOGen implements BookDAO {
  @Autowired
  public JDBCBookDAO(DataSource dseDataSource) {
    super(dseDataSource);
    build();
  }

  public List<Book> getAllBooks() {
    Substitution[] substitutions = new Substitution[0];
    List<Map<String, Object>> results = (List)exicuteQuery("getAllBooks", substitutions);
    return this.map(results);
  }

  public List<Book> getBooksByRussell(String author) {
    Substitution[] substitutions = new Substitution[1];
    substitutions[0] = new Substitution("author", author, 0);
    List<Map<String, Object>> results = (List)exicuteQuery("getBooksByRussell", substitutions);
    return this.map(results);
  }

  public List<Book> map(List results) {
    List<Book> list = new ArrayList<Book>();
    ;
    for(Map<String, Object> row : ((List<Map<String, Object>>)results))
        list.add(mapRow(row));
    return list;
  }

  private Book mapRow(Map row) {
    Book bean = new Book();
    String resultStr;
    resultStr = extractResult(row, "author");
    bean.setAuthor(resultStr);
    resultStr = extractResult(row, "price");
    bean.setPrice(resultStr);
    resultStr = extractResult(row, "title");
    bean.setTiTle(resultStr);
    resultStr = extractResult(row, "publish_date");
    bean.setPublishDate(resultStr);
    resultStr = extractResult(row, "isbn_13");
    bean.setIsbn13(resultStr);
    return bean;
  }

  private void build() {
    this.addQuery("getAllBooks", " Select isbn_13, title, author from books ");
    this.addColumn("getAllBooks", "isbn_13");
    this.addColumn("getAllBooks", "title");
    this.addColumn("getAllBooks", "author");
    this.addQuery("getBooksByRussell", " Select publish_date, price, isbn_13 from books where $$author$$ = author ");
    this.addSubstitute("getBooksByRussell", "author");
    this.addColumn("getBooksByRussell", "isbn_13");
    this.addColumn("getBooksByRussell", "title");
    this.addColumn("getBooksByRussell", "publish_date");
    this.addColumn("getBooksByRussell", "price");
  }
}
