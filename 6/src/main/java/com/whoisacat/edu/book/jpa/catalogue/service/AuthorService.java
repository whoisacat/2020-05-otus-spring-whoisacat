package com.whoisacat.edu.book.jpa.catalogue.service;

import com.whoisacat.edu.book.jpa.catalogue.domain.Author;

public interface AuthorService extends TitledService<Author>{

    String getAllAuthorsString();

    Author findByNameOrCreate(String authorString);

    long getAuthorsCount();

    String findByName(String name);
}
