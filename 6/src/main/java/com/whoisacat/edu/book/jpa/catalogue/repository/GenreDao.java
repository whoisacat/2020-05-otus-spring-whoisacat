package com.whoisacat.edu.book.jpa.catalogue.repository;

import com.whoisacat.edu.book.jpa.catalogue.domain.Genre;

import java.util.List;

public interface GenreDao{

    long count();

    Genre save(Genre genre);

    Genre getById(long id);

    List<Genre> getByName(String name);

    List<Genre> getAll();

    int deleteById(long id);
}
