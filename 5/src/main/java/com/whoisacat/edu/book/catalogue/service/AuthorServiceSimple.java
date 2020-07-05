package com.whoisacat.edu.book.catalogue.service;

import com.whoisacat.edu.book.catalogue.dao.AuthorDao;
import com.whoisacat.edu.book.catalogue.domain.Author;
import com.whoisacat.edu.book.catalogue.domain.Book;
import com.whoisacat.edu.book.catalogue.service.exception.WHOAuthorAlreadyExists;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceSimple implements AuthorService{

    public static final String NOT_FOUND = "Не найден";
    private final AuthorDao dao;

    public AuthorServiceSimple(AuthorDao dao){
        this.dao = dao;
    }

    @Override
    public String getAllAuthorsString(){
        List<Author> authorsList = dao.getAll();
        StringBuilder sb = new StringBuilder();
        for(Author author : authorsList){
            sb.append(author.getName()).append(' ').append("Книги: ");
            for(Book book : author.getBooks()){
                sb.append(book.getName()).append(';').append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override public Author findByNameOrCreate(String authorString){
        List<Author> authors = dao.getByName(authorString);
        if(authors.size() == 1){
            return authors.get(0);
        }
        if(authors.size() > 1){
            throw new WHOAuthorAlreadyExists();
        }
        Author author = new Author(null,authorString,new ArrayList<>());
        Long authorId = dao.insert(author);
        return dao.getById(authorId);
    }

    @Override public long getAuthorsCount(){
        return dao.count();
    }

    @Override public String findByName(String name){
        List<Author> authors = dao.getByName(name);
        if(authors.isEmpty()){
            return NOT_FOUND;
        }
        return buildNames(authors);
    }
}
