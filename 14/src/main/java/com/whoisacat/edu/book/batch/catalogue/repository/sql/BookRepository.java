package com.whoisacat.edu.book.batch.catalogue.repository.sql;

import com.whoisacat.edu.book.batch.catalogue.domain.sql.BookSQL;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookSQL, Long> {

}
