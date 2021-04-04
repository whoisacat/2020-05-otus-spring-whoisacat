package com.whoisacat.edu.book.batch.catalogue.repository.mongo;

import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreMongoRepository extends MongoRepository<Genre,String>{

}
