package com.whoisacat.edu.book.jpa.catalogue.service;

import com.whoisacat.edu.book.jpa.catalogue.domain.Genre;

public interface GenreService extends TitledService<Genre>{

    Genre findByNameOrCreate(String authorString);

    long getGenresCount();

    String getAllGenresString();

    String findByName(String name);
}
