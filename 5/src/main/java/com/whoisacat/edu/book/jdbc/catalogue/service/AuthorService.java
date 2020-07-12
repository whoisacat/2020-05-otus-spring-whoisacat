package com.whoisacat.edu.book.jdbc.catalogue.service;

import com.whoisacat.edu.book.jdbc.catalogue.domain.Author;

public interface AuthorService extends NamedService<Author>{

    String getAllAuthorsString();

    Author findByNameOrCreate(String authorString);

    long getAuthorsCount();

    String findByName(String name);
}
