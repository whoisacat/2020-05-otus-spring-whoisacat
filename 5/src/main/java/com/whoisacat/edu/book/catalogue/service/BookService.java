package com.whoisacat.edu.book.catalogue.service;

import com.whoisacat.edu.book.catalogue.domain.Book;

import java.util.List;

public interface BookService{

    List<Book> findAll();

    String addBook(String book,String author,String genre);

    long getBooksCount();

    List<Book> findByAuthorId(long id);
}
