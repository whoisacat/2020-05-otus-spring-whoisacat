package com.whoisacat.edu.book.batch.catalogue.repository.sql;

import com.whoisacat.edu.book.batch.catalogue.domain.sql.GenreSQL;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<GenreSQL,Long> {

    GenreSQL findByMongoId(String id);
}
