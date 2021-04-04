package com.whoisacat.edu.book.batch.catalogue.repository.sql;

import com.whoisacat.edu.book.batch.catalogue.domain.sql.BookSQL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "books")
public interface BookRepository extends CrudRepository<BookSQL, Long> {

    @RestResource(path = "/all", rel = "all")
    List<BookSQL> findAll();
}
