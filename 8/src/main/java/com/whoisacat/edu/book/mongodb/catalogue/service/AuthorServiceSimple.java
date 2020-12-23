package com.whoisacat.edu.book.mongodb.catalogue.service;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Author;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Book;
import com.whoisacat.edu.book.mongodb.catalogue.service.exception.WHOAuthorAlreadyExistsException;
import com.whoisacat.edu.book.mongodb.catalogue.repository.AuthorMongoRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceSimple implements AuthorService{

    public static final String NOT_FOUND = "Не найден";
    private final AuthorMongoRepository repository;
    private final BookService bookService;

    public AuthorServiceSimple(AuthorMongoRepository repository,@Lazy BookService bookService){
        this.repository = repository;
        this.bookService = bookService;
    }

    @Override
    public String getAllAuthorsString(){
        List<Author> authorsList = repository.getAllBy();
        StringBuilder sb = new StringBuilder();
        for(Author author : authorsList){
            sb.append(author.getTitle()).append(' ').append("Книги: ");
            List <Book> books = bookService.findByAuthorTitle(author.getTitle());
            for(Book book : books){
                sb.append(book.getTitle()).append(';').append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override public Author findByNameOrCreate(String authorString){
        List<Author> authors = repository.getByTitleContains(authorString);
        if(authors.size() == 1){
            return authors.get(0);
        }
        if(authors.size() > 1){
            throw new WHOAuthorAlreadyExistsException();
        }
        Author author = new Author(authorString);
        return repository.save(author);
    }

    @Override public long getAuthorsCount(){
        return repository.count();
    }

    @Override public String findAuthorsByTitle(String title){
        List<Author> authors = repository.getByTitleContains(title);
        if(authors.isEmpty()){
            return NOT_FOUND;
        }
        return buildNames(authors);
    }

    @Override public List<Author> findAuthorsWithSubstringInTitle(String substring){
        return repository.getByTitleContains(substring);
    }
}
