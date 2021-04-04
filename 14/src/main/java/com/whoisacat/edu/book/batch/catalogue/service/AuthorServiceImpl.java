package com.whoisacat.edu.book.batch.catalogue.service;

import com.whoisacat.edu.book.batch.catalogue.domain.sql.AuthorSQL;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Author;
import com.whoisacat.edu.book.batch.catalogue.repository.sql.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override public AuthorSQL convert(Author author) {
        AuthorSQL authorSQL = new AuthorSQL(null, author.getTitle(), author.getId());
        return repository.save(authorSQL);
    }

    @Override public AuthorSQL findByMongoId(String mongoId) {
        return repository.findByMongoId(mongoId);
    }
}
