package com.whoisacat.edu.book.jpa.catalogue.repository;

import com.whoisacat.edu.book.jpa.catalogue.domain.Author;

import java.util.List;

public interface AuthorRepository{

    Long count();

    Author save(Author author);

    Author getById(long id);

    List<Author> getByName(String name);

    List<Author> getAll();

    int deleteById(long id);
}
