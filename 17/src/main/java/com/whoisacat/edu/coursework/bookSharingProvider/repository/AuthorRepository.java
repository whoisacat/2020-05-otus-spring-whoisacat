package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Author;
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
