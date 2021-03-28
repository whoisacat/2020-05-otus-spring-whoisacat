package com.whoisacat.edu.book.batch.catalogue.repository.sql;

import com.whoisacat.edu.book.batch.catalogue.domain.sql.AuthorSQL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "authors")
public interface AuthorRepository extends CrudRepository<AuthorSQL,Long>{

    AuthorSQL findByMongoId(String mongoId);

    @RestResource(path = "all", rel = "all")
    List<AuthorSQL> findAll();
}
