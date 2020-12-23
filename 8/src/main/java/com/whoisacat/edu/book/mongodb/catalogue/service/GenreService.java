package com.whoisacat.edu.book.mongodb.catalogue.service;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Genre;

public interface GenreService extends NamedService<Genre>{

    Genre findByNameOrCreate(String authorString);

    long getGenresCount();

    String getAllGenresString();

    String findByName(String name);
}
