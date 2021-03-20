package com.whoisacat.edu.book.batch.catalogue.service;

import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Author;
import com.whoisacat.edu.book.batch.catalogue.domain.sql.AuthorSQL;

import java.util.List;

public interface AuthorService {

    AuthorSQL convert(Author author);

    AuthorSQL findByMongoId(String mongoId);
}
