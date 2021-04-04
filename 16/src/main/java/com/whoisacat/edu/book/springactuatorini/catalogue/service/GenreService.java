package com.whoisacat.edu.book.springactuatorini.catalogue.service;

import com.whoisacat.edu.book.springactuatorini.catalogue.domain.Genre;

public interface GenreService extends NamedService<Genre>{

    Genre findByNameOrCreate(String authorString);

    long getGenresCount();

    String getAllGenresString();

    String findByName(String name);

    Genre update(Genre genre);
}
