package com.whoisacat.edu.book.catalogue.service;

import com.whoisacat.edu.book.catalogue.dao.GenreDao;
import com.whoisacat.edu.book.catalogue.domain.Genre;
import com.whoisacat.edu.book.catalogue.service.exception.WHOGenreAlreadyExists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceSimple implements GenreService{

    private final GenreDao dao;

    public GenreServiceSimple(GenreDao dao){
        this.dao = dao;
    }

    @Override public Genre findByNameOrCreate(String authorString){
        List<Genre> genres = dao.getByName(authorString);
        if(genres.size() == 1){
            return genres.get(0);
        }
        if(genres.size() > 1){
            throw new WHOGenreAlreadyExists();
        }
        Genre author = new Genre(null,authorString);
        long id = dao.insert(author);
        return dao.getById(id);
    }

    @Override public long getGenresCount(){
        return dao.count();
    }

    @Override public String getAllGenresString(){
        List<Genre> genresList = dao.getAll();
        return buildNames(genresList);
    }

    @Override public String findByName(String name){
        List<Genre> genresList = dao.getByName(name);
        return buildNames(genresList);
    }
}
