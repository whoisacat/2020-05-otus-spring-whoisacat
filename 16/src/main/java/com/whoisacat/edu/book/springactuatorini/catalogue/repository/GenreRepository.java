package com.whoisacat.edu.book.springactuatorini.catalogue.repository;

import com.whoisacat.edu.book.springactuatorini.catalogue.domain.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends CrudRepository<Genre,Long>{

    long count();

    @RestResource(path = "id", rel = "id")
    Genre getById(long id);

    @RestResource(path = "title", rel = "title")
    List<Genre> getByTitle(String title);

    @RestResource(path = "all", rel = "all")
    List<Genre> getAllBy();

    void deleteById(long id);
}
