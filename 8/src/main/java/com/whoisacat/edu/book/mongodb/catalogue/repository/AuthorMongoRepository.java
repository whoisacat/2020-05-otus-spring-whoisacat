package com.whoisacat.edu.book.mongodb.catalogue.repository;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthorMongoRepository extends MongoRepository<Author,String>{

    long count();

    Author getById(String id);

    List<Author> getByTitleContains(String title);

    List<Author> getAllBy();

    void deleteById(String id);

    void deleteAuthorByTitleLike(String title);
}
