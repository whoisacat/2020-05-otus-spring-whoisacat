package com.whoisacat.edu.book.jdbc.catalogue.dao;

import com.whoisacat.edu.book.jdbc.catalogue.domain.Genre;

import java.util.List;

public interface GenreDao{

    long count();

    Long insert(Genre genre);

    Genre getById(long id);

    List<Genre> getByName(String name);

    List<Genre> getAll();

    void deleteById(long id);
}
