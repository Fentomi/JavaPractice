package test.spring.springTest.controller;

import org.springframework.web.bind.annotation.*;
import test.spring.springTest.model.Book;
import test.spring.springTest.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

  private BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping
  public Book createBook(@RequestBody Book book) {
    return bookService.saveBook(book);
  }

  @GetMapping
  public List<Book> getAllBooks() {
    return bookService.findAll();
  }

  @GetMapping("/{id}")
  public Book getBookById(@PathVariable int id) {
    return bookService.findBookById(id);
  }

  @PutMapping("/{id}")
  public Book updateBook(@PathVariable int id, @RequestBody Book book) {
    book.setId(id);
    return bookService.update(book);
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable int id) {
    bookService.delete(id);
  }

}
