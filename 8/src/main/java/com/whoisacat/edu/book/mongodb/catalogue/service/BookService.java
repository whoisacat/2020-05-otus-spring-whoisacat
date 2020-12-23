package com.whoisacat.edu.book.mongodb.catalogue.service;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Book;

import java.util.List;

public interface BookService extends NamedService<Book>{

    List<Book> findAll();

    Book addBook(String book,String author,String genre);

    long getBooksCount();

    List<Book> findByAuthorTitle(String title);

    Book findByOwnAndAuthorAndGenreTitles(String title,String authorTitle,String genreTitle);

    List<Book> findBooksWithSubstringInTitle(String substring);

    String buildBooksString(List<Book> existedBooks);

    String getAllBooksString();
}
