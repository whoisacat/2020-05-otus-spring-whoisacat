package com.whoisacat.edu.book.springdata.catalogue.service;

import com.whoisacat.edu.book.springdata.catalogue.repository.GenreRepository;
import com.whoisacat.edu.book.springdata.catalogue.domain.Genre;
import com.whoisacat.edu.book.springdata.catalogue.service.exception.WHOGenreAlreadyExists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreServiceSimple implements GenreService{

    private final GenreRepository dao;

    public GenreServiceSimple(GenreRepository dao){
        this.dao = dao;
    }

    @Transactional
    @Override public Genre findByNameOrCreate(String authorString){
        List<Genre> genres = dao.getByTitle(authorString);
        if(genres.size() == 1){
            return genres.get(0);
        }
        if(genres.size() > 1){
            throw new WHOGenreAlreadyExists();
        }
        Genre author = new Genre(null,authorString);
        long id = dao.save(author).getId();
        return dao.getById(id);
    }

    @Override public long getGenresCount(){
        return dao.count();
    }

    @Override public String getAllGenresString(){
        List<Genre> genresList = dao.getAllBy();
        return buildNames(genresList);
    }

    @Transactional(readOnly = true)
    @Override public String findByName(String name){
        List<Genre> genresList = dao.getByTitle(name);
        return buildNames(genresList);
    }
}
