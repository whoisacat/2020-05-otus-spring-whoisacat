package com.whoisacat.edu.book.batch.catalogue.repository.sql;

import com.whoisacat.edu.book.batch.catalogue.domain.sql.GenreSQL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends CrudRepository<GenreSQL,Long> {

    GenreSQL findByMongoId(String id);

    @RestResource(path = "all", rel = "all")
    List<GenreSQL> findAll();
}
