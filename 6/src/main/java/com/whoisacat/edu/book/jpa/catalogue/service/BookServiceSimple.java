package com.whoisacat.edu.book.jpa.catalogue.service;

import com.whoisacat.edu.book.jpa.catalogue.dao.BookDao;
import com.whoisacat.edu.book.jpa.catalogue.domain.Author;
import com.whoisacat.edu.book.jpa.catalogue.domain.Book;
import com.whoisacat.edu.book.jpa.catalogue.domain.Genre;
import com.whoisacat.edu.book.jpa.catalogue.service.exception.WHOBookAlreadyExists;
import com.whoisacat.edu.book.jpa.catalogue.service.exception.WHODataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceSimple implements BookService{

    private final BookDao dao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceSimple(BookDao dao,AuthorService authorService,
                             GenreService genreService){
        this.dao = dao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override public List<Book> findAll(){
        return dao.getAll();
    }

    @Override public Book addBook(String bookString,String authorString,String genreString){
        Author author = authorService.findByNameOrCreate(authorString);
        Genre genre = genreService.findByNameOrCreate(genreString);
        List<Book> existedBooks = dao.findByNameAndAuthorIdAndGenreId(bookString,author.getId(),genre.getId());
        if(!existedBooks.isEmpty()){
            throw new WHOBookAlreadyExists();
        }
        Long id = dao.insert(new Book(null,bookString,author,genre));
        if(id == null){
            throw new WHODataAccessException("bookDaoInsertHasReturnedNull");
        }
        return dao.getById(id);
    }

    @Override public long getBooksCount(){
        return dao.count();
    }

    @Override public List<Book> findByAuthorId(long id){
        return dao.getByAuthor(id);
    }

    @Override
    public String buildBooksString(List<Book> existedBooks){
        StringBuilder sb = new StringBuilder();
        for(Book book : existedBooks){
            sb.append(book.getName())
                    .append(" ")
                    .append(book.getAuthor().getName())
                    .append(" ")
                    .append(book.getGenre().getName())
                    .append("; ");
        }
        return sb.toString();
    }
}
