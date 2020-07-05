package com.whoisacat.edu.book.catalogue.service;

import com.whoisacat.edu.book.catalogue.domain.Book;

import java.util.List;

public interface BookService extends NamedService<Book>{

    List<Book> findAll();

    Book addBook(String book,String author,String genre);

    long getBooksCount();

    List<Book> findByAuthorId(long id);

    String buildBooksString(List<Book> existedBooks);
}
