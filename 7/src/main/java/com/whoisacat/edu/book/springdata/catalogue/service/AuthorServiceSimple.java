package com.whoisacat.edu.book.springdata.catalogue.service;

import com.whoisacat.edu.book.springdata.catalogue.domain.Book;
import com.whoisacat.edu.book.springdata.catalogue.repository.AuthorRepository;
import com.whoisacat.edu.book.springdata.catalogue.domain.Author;
import com.whoisacat.edu.book.springdata.catalogue.service.exception.WHOAuthorAlreadyExists;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceSimple implements AuthorService{

    public static final String NOT_FOUND = "Не найден";
    private final AuthorRepository dao;
    private final BookService bookService;

    public AuthorServiceSimple(AuthorRepository dao,@Lazy BookService bookService){
        this.dao = dao;
        this.bookService = bookService;
    }

    @Override
    @Transactional
    public String getAllAuthorsString(){
        List<Author> authorsList = dao.getAllBy();
        StringBuilder sb = new StringBuilder();
        for(Author author : authorsList){
            sb.append(author.getTitle()).append(' ').append("Книги: ");
            List <Book> books = bookService.findByAuthorId(author.getId());
            for(Book book : books){
                sb.append(book.getTitle()).append(';').append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Transactional
    @Override public Author findByNameOrCreate(String authorString){
        List<Author> authors = dao.getByTitle(authorString);
        if(authors.size() == 1){
            return authors.get(0);
        }
        if(authors.size() > 1){
            throw new WHOAuthorAlreadyExists();
        }
        Author author = new Author(null,authorString);
        return dao.save(author);
    }

    @Override public long getAuthorsCount(){
        return dao.count();
    }

    @Transactional(readOnly = true)
    @Override public String findByName(String name){
        List<Author> authors = dao.getByTitle(name);
        if(authors.isEmpty()){
            return NOT_FOUND;
        }
        return buildNames(authors);
    }
}
