package com.whoisacat.edu.book.springmvcini.catalogue.repository;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre,String>{

    long count();

    Genre getById(String id);

    List<Genre> getByTitle(String title);

    List<Genre> getByTitleContains(String title);

    List<Genre> getAllBy();

    void deleteByTitle(String title);
}
