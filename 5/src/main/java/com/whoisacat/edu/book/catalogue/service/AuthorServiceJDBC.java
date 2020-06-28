package com.whoisacat.edu.book.catalogue.service;

import com.whoisacat.edu.book.catalogue.dao.AuthorDao;
import com.whoisacat.edu.book.catalogue.domain.Author;
import com.whoisacat.edu.book.catalogue.domain.Book;
import com.whoisacat.edu.book.catalogue.service.exception.WHORequestClientException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceJDBC implements AuthorService, NamedService<Author>{

    public static final String NOT_FOUND = "Не найден";
    private final AuthorDao dao;

    public AuthorServiceJDBC(AuthorDao dao){
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
            StringBuilder builder = buildNames(authors);
            throw new WHORequestClientException("Выберите автора точнее, были найдены - ".concat(builder.toString()));
        }
        Author author = new Author(dao.count() + 1,authorString,new ArrayList<>());
        dao.insert(author);
        return findByNameOrCreate(authorString);
    }

    @Override public long getAuthorsCount(){
        return dao.count();
    }

    @Override public String findByName(String name){
        List<Author> authors = dao.getByName(name);
        if(authors.isEmpty()){
            return NOT_FOUND;
        }
        StringBuilder authorsSB = buildNames(authors);
        return authorsSB.toString();
    }
}
