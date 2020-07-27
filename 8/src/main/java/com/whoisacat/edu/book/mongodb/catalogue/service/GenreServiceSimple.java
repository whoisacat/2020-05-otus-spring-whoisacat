package com.whoisacat.edu.book.mongodb.catalogue.service;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Genre;
import com.whoisacat.edu.book.mongodb.catalogue.service.exception.WHOGenreAlreadyExistsException;
import com.whoisacat.edu.book.mongodb.catalogue.repository.GenreMongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreServiceSimple implements GenreService{

    private final GenreMongoRepository repository;

    public GenreServiceSimple(GenreMongoRepository repository){
        this.repository = repository;
    }

    @Transactional
    @Override public Genre findByNameOrCreate(String authorString){
        List<Genre> genres = repository.getByTitleContains(authorString);
        if(genres.size() == 1){
            return genres.get(0);
        }
        if(genres.size() > 1){
            throw new WHOGenreAlreadyExistsException();
        }
        Genre author = new Genre(null,authorString);
        String id = repository.save(author).getId();
        return repository.getById(id);
    }

    @Override public long getGenresCount(){
        return repository.count();
    }

    @Override public String getAllGenresString(){
        List<Genre> genresList = repository.getAllBy();
        return buildNames(genresList);
    }

    @Transactional(readOnly = true)
    @Override public String findByName(String name){
        List<Genre> genresList = repository.getByTitleContains(name);
        return buildNames(genresList);
    }
}
