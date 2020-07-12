package com.whoisacat.edu.book.jpa.catalogue.dao;

import com.whoisacat.edu.book.jpa.catalogue.domain.Author;

import java.util.List;

public interface AuthorDao{

    long count();

    Long insert(Author author);

    Author getById(long id);

    List<Author> getByName(String name);

    List<Author> getAll();

    void deleteById(long id);
}
