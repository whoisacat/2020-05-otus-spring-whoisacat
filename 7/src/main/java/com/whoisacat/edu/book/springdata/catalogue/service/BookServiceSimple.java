package com.whoisacat.edu.book.springdata.catalogue.service;

import com.whoisacat.edu.book.springdata.catalogue.repository.BookRepository;
import com.whoisacat.edu.book.springdata.catalogue.domain.Author;
import com.whoisacat.edu.book.springdata.catalogue.domain.Book;
import com.whoisacat.edu.book.springdata.catalogue.domain.Genre;
import com.whoisacat.edu.book.springdata.catalogue.service.exception.WHOBookAlreadyExists;
import com.whoisacat.edu.book.springdata.catalogue.service.exception.WHODataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceSimple implements BookService{

    private final BookRepository repository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceSimple(BookRepository repository,AuthorService authorService,
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
        List<Book> existedBooks = repository.findByTitleContainsAndAuthorIdAndGenreId(bookString,author.getId(),genre.getId());
        if(!existedBooks.isEmpty()){
            throw new WHOBookAlreadyExists();
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
    @Override public List<Book> findByAuthorId(long id){
        return repository.getByAuthorId(id);
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
