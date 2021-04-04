package com.whoisacat.edu.book.springactuatorini.catalogue.service;

import com.whoisacat.edu.book.springactuatorini.catalogue.repository.GenreRepository;
import com.whoisacat.edu.book.springactuatorini.catalogue.domain.Genre;
import com.whoisacat.edu.book.springactuatorini.catalogue.service.exception.WHOGenreAlreadyExists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreServiceMvc implements GenreService{

    private final GenreRepository repository;

    public GenreServiceMvc(GenreRepository repository){
        this.repository = repository;
    }

    @Transactional
    @Override public Genre findByNameOrCreate(String authorString){
        List<Genre> genres = repository.getByTitle(authorString);
        if(genres.size() == 1){
            return genres.get(0);
        }
        if(genres.size() > 1){
            throw new WHOGenreAlreadyExists();
        }
        Genre author = new Genre(null,authorString);
        long id = repository.save(author).getId();
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
        List<Genre> genresList = repository.getByTitle(name);
        return buildNames(genresList);
    }

    @Override public Genre update(Genre genre){
        return repository.save(genre);
    }
}
