package test.spring.springTest.repository;

import org.springframework.stereotype.Repository;
import test.spring.springTest.model.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {

  private final Map<Integer, Book> storage = new HashMap<>();
  private int counter = 3;

  public BookRepository() {
    storage.put(1, new Book(1, "Евгений Онегин", "Пушкин Александр Сергеевич"));
    storage.put(2, new Book(2, "Книжный вор", "Маркус Зусак"));
  }

  public int saveBook(Book book) {
    if (book.getId() == 0) book.setId(counter++);
    storage.put(book.getId(), book);
    return book.getId();
  }

  public int deleteById(int bookId) {
    storage.remove(bookId);
    return bookId;
  }

  public List<Book> findAll() {
    return storage.values().stream().toList();
  }

  public Book findById(int bookId) throws NullPointerException {
    return storage.get(bookId);
  }

  public int update(Book book) {
    deleteById(book.getId());
    saveBook(book);
    return book.getId();
  }

}
