package com.whoisacat.edu.book.springmvcini.catalogue.service;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Author;

import java.util.List;

public interface AuthorService extends NamedService<Author>{

    String getAllAuthorsString();

    Author findByNameOrCreate(String authorString);

    long getAuthorsCount();

    String findByName(String name);

    Author update(Author author);

    List<Author> findAuthorsWithSubstringInTitle(String substring);
}
