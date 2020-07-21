package com.whoisacat.edu.book.jpa.catalogue.repository;

import com.whoisacat.edu.book.jpa.catalogue.domain.Book;

import java.util.List;

public interface BookRepository{

    long count();

    Book save(Book book);

    Book getById(long id);

    List<Book> getByName(String name);

    List<Book> getByAuthor(long author_id);

    List<Book> getAll();

    int deleteById(long id);

    int deleteByName(String name);

    List<Book> findByNameAndAuthorIdAndGenreId(String bookString,long authorId,long genreId);
}
