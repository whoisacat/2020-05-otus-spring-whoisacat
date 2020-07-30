package com.whoisacat.edu.book.springdata.catalogue.service;

import com.whoisacat.edu.book.springdata.catalogue.domain.Author;

public interface AuthorService extends NamedService<Author>{

    String getAllAuthorsString();

    Author findByNameOrCreate(String authorString);

    long getAuthorsCount();

    String findByName(String name);
}
