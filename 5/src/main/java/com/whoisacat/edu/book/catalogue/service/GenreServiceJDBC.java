package com.whoisacat.edu.book.catalogue.service;

import com.whoisacat.edu.book.catalogue.dao.GenreDao;
import com.whoisacat.edu.book.catalogue.domain.Genre;
import com.whoisacat.edu.book.catalogue.service.exception.WHORequestClientException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceJDBC implements GenreService,NamedService<Genre>{

    private final GenreDao dao;

    public GenreServiceJDBC(GenreDao dao){
        this.dao = dao;
    }

    @Override public Genre findByNameOrCreate(String authorString){
        List<Genre> genres = dao.getByName(authorString);
        if(genres.size() == 1){
            return genres.get(0);
        }
        if(genres.size() > 1){
            StringBuilder builder = buildNames(genres);
            throw new WHORequestClientException("Выберите жанр точнее, были найдены - ".concat(builder.toString()));
        }
        Genre author = new Genre(dao.count() + 1,authorString);
        dao.insert(author);
        return findByNameOrCreate(authorString);
    }

    @Override public long getGenresCount(){
        return dao.count();
    }

    @Override public String getAllGenresString(){
        List<Genre> genresList = dao.getAll();
        StringBuilder sb = buildNames(genresList);
        return sb.toString();
    }

    @Override public String findByName(String name){

        List<Genre> genresList = dao.getByName(name);
        StringBuilder sb = buildNames(genresList);
        return sb.toString();
    }
}
