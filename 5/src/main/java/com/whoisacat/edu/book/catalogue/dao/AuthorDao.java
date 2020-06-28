package com.whoisacat.edu.book.catalogue.dao;

import com.whoisacat.edu.book.catalogue.domain.Author;

import java.util.List;

public interface AuthorDao{

    int count();

    void insert(Author author);

    Author getById(long id);

    List<Author> getByName(String name);

    List<Author> getAll();

    void deleteById(long id);
}
