package com.whoisacat.edu.book.springmvcini.catalogue.service;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import com.whoisacat.edu.book.springmvcini.catalogue.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService extends NamedService<Book>{

    Page<Book> findAll(Pageable pageable);

    Book addBook(String book,String author,String genre);

    long getBooksCount();

    List<Book> findByAuthorId(long id);

    String buildBooksString(List<Book> existedBooks);

    Optional<Book> findById(long id);

    Book update(BookDTO book);

    void delete(long id);
}
