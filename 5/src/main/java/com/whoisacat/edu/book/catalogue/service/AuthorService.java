package com.whoisacat.edu.book.catalogue.service;

import com.whoisacat.edu.book.catalogue.domain.Author;

public interface AuthorService{

    String getAllAuthorsString();

    Author findByNameOrCreate(String authorString);

    long getAuthorsCount();

    String findByName(String name);
}
