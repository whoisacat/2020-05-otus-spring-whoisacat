package com.whoisacat.edu.book.jpa.catalogue.service;

import com.whoisacat.edu.book.jpa.catalogue.domain.Book;

import java.util.List;

public interface BookService extends TitledService<Book>{

    List<Book> findAll();

    Book addBook(String book,String author,String genre);

    long getBooksCount();

    List<Book> findByAuthorId(long id);

    String buildBooksString(List<Book> existedBooks);

    String getAllBooksString();
}
