package com.whoisacat.edu.book.batch.catalogue.repository.mongo;

import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorMongoRepository extends MongoRepository<Author,String>{

}
