package com.whoisacat.edu.book.batch.catalogue.service;

import com.whoisacat.edu.book.batch.catalogue.domain.sql.AuthorSQL;
import com.whoisacat.edu.book.batch.catalogue.domain.sql.BookSQL;
import com.whoisacat.edu.book.batch.catalogue.domain.sql.GenreSQL;
import com.whoisacat.edu.book.batch.catalogue.repository.sql.BookRepository;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Book;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository repository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookRepository repository,AuthorService authorService,
                             GenreService genreService){
        this.repository = repository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override public BookSQL convert(Book book) {
        AuthorSQL authorSQL = authorService.findByMongoId(book.getAuthor().getId());
        GenreSQL genreSQL = genreService.findByMongoId(book.getGenre().getId());
        BookSQL bookSQL = new BookSQL(null, book.getTitle(), authorSQL, genreSQL);
        return repository.save(bookSQL);
    }
}
