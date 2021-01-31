package com.whoisacat.edu.book.springmvcini.catalogue.repository;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author,String>{

    long count();

    Author getById(String id);

    List<Author> getByTitleContains(String title);

    List<Author> getAllBy();

    void deleteById(String id);

    void deleteAuthorByTitleLike(String title);

    List<Author> getByTitle(String name);
}
