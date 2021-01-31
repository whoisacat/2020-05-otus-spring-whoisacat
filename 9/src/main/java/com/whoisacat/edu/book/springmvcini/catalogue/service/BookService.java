package com.whoisacat.edu.book.springmvcini.catalogue.service;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import com.whoisacat.edu.book.springmvcini.catalogue.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface BookService extends NamedService<Book>{

    Page<Book> findAll(PageRequest pageRequest);

    Book addBook(String book,String author,String genre);

    long getBooksCount();

    List<Book> findByAuthorId(String id);

    String buildBooksString(List<Book> existedBooks);

    Optional<Book> findById(String id);

    Book update(BookDTO book);


    void delete(String id);

    List<Book> findBooksWithSubstringInTitle(String bookTitle);

    Book findByOwnAndAuthorAndGenreTitles(String bookTitle, String bookAuthor, String bookGenre);
}
