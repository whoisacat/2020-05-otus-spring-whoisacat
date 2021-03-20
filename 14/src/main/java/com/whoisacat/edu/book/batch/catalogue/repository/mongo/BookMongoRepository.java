package com.whoisacat.edu.book.batch.catalogue.repository.mongo;

import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookMongoRepository extends MongoRepository<Book,String>{

}
