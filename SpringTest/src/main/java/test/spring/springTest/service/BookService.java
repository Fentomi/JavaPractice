package test.spring.springTest.service;

import org.springframework.stereotype.Service;
import test.spring.springTest.model.Book;
import test.spring.springTest.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

  private BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Book saveBook(Book book) {
    if (book.getTitle() == null || book.getTitle().isBlank())
      throw new IllegalArgumentException("Заголовок не может быть пустым или null");
    if (book.getAuthor() == null || book.getAuthor().isBlank())
      throw new IllegalArgumentException("Попытка сохранить книгу с пустым полем автора");

    bookRepository.saveBook(book);
    return book;
  }

  public Book findBookById(int bookId) throws NullPointerException {
    return bookRepository.findById(bookId);
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public void delete(int bookId) throws NullPointerException {
    bookRepository.findById(bookId);
    bookRepository.deleteById(bookId);
  }

  public Book update(Book book) {
    bookRepository.update(book);
    return book;
  }

}
