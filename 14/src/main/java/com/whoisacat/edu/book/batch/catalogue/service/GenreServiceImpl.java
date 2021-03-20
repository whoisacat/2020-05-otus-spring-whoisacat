package com.whoisacat.edu.book.batch.catalogue.service;

import com.whoisacat.edu.book.batch.catalogue.domain.sql.GenreSQL;
import com.whoisacat.edu.book.batch.catalogue.repository.sql.GenreRepository;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Genre;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository){
        this.repository = repository;
    }

    @Override public GenreSQL convert(Genre genre) {
        GenreSQL genreSQL = new GenreSQL(null, genre.getTitle(), genre.getId());
        return repository.save(genreSQL);
    }

    @Override public GenreSQL findByMongoId(String id) {
        return repository.findByMongoId(id);
    }
}
