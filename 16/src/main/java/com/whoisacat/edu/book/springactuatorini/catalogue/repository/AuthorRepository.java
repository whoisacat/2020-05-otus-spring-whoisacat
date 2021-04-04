package com.whoisacat.edu.book.springactuatorini.catalogue.repository;

import com.whoisacat.edu.book.springactuatorini.catalogue.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "authors")
public interface AuthorRepository extends CrudRepository<Author,Long>{

    long count();

    @RestResource(path = "id", rel = "id")
    Author getById(long id);

    @RestResource(path = "title", rel = "title")
    List<Author> getByTitle(String title);

    @RestResource(path = "all", rel = "all")
    List<Author> getAllBy();

    void deleteById(long id);
}
