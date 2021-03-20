package com.whoisacat.edu.book.batch.catalogue.repository.sql;

import com.whoisacat.edu.book.batch.catalogue.domain.sql.AuthorSQL;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorSQL,Long>{

    AuthorSQL findByMongoId(String mongoId);
}
