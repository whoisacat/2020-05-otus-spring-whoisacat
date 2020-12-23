package com.whoisacat.edu.book.mongodb.catalogue.repository;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GenreMongoRepository extends MongoRepository<Genre,String>{

    long count();

    Genre getById(String id);

    Optional<Genre> getByTitle(String title);

    List<Genre> getByTitleContains(String title);

    List<Genre> getAllBy();

    void deleteByTitle(String title);
}
