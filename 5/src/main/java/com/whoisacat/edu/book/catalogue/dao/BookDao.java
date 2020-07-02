package com.whoisacat.edu.book.catalogue.dao;

import com.whoisacat.edu.book.catalogue.domain.Book;

import java.util.List;

public interface BookDao{

    long count();

    Long insert(Book book);

    Book getById(long id);

    List<Book> getByName(String name);

    List<Book> getByAuthor(long author_id);

    List<Book> getAll();

    void deleteById(long id);

    void deleteByName(String name);

    List<Book> findByNameAndAuthorIdAndGenreId(String bookString,long authorId,long genreId);
}
