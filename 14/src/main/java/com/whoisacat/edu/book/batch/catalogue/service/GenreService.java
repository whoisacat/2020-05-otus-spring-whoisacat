package com.whoisacat.edu.book.batch.catalogue.service;

import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Genre;
import com.whoisacat.edu.book.batch.catalogue.domain.sql.GenreSQL;

public interface GenreService {

    GenreSQL convert(Genre genre);

    GenreSQL findByMongoId(String id);
}
