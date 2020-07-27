package com.whoisacat.edu.book.mongodb.catalogue.service;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Author;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Book;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Genre;
import com.whoisacat.edu.book.mongodb.catalogue.service.exception.WHODataAccessException;
import com.whoisacat.edu.book.mongodb.catalogue.repository.BookMongoRepository;
import com.whoisacat.edu.book.mongodb.catalogue.service.exception.WHOBookAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceSimple implements BookService{

    private final BookMongoRepository repository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceSimple(BookMongoRepository repository,AuthorService authorService,
                             GenreService genreService){
        this.repository = repository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override public List<Book> findAll(){
        return repository.getAllBy();
    }

    @Transactional
    @Override public Book addBook(String bookString,String authorString,String genreString){
        Author author = authorService.findByNameOrCreate(authorString);
        Genre genre = genreService.findByNameOrCreate(genreString);
        List<Book> existedBooks = repository
                .findByTitleContainsAndAuthorTitleLikeAndGenreTitleLike(bookString,author.getTitle(),genre.getTitle());
        if(!existedBooks.isEmpty()){
            throw new WHOBookAlreadyExistsException();
        }
        Book book = repository.save(new Book(null,bookString,author,genre));
        if(book == null){
            throw new WHODataAccessException("bookDaoInsertHasReturnedNull");
        }
        return book;
    }

    @Override public long getBooksCount(){
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override public List<Book> findByAuthorTitle(String title){
        return repository.getByAuthor_Title(title);
    }

    @Override public Book findByOwnAndAuthorAndGenreTitles(String title,String authorTitle,String genreTitle){
        return repository
                .findByTitleLikeAndAuthorTitleLikeAndGenreTitleLike(title,authorTitle,genreTitle)
                .orElseThrow();
    }

    @Override public List<Book> findBooksWithSubstringInTitle(String substring){
        return repository.getAllByTitleContains(substring);
    }

    @Override
    public String buildBooksString(List<Book> existedBooks){
        StringBuilder sb = new StringBuilder();
        for(Book book : existedBooks){
            sb.append(book.getTitle())
                    .append(" ")
                    .append(book.getAuthor().getTitle())
                    .append(" ")
                    .append(book.getGenre().getTitle())
                    .append("; ");
        }
        return sb.toString();
    }

    @Transactional
    @Override public String getAllBooksString(){
        return buildBooksString(findAll());
    }
}
