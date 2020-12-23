package com.whoisacat.edu.book.mongodb.catalogue.service;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Author;

import java.util.List;

public interface AuthorService extends NamedService<Author>{

    String getAllAuthorsString();

    Author findByNameOrCreate(String authorString);

    long getAuthorsCount();

    String findAuthorsByTitle(String name);

    List<Author> findAuthorsWithSubstringInTitle(String substring);
}
