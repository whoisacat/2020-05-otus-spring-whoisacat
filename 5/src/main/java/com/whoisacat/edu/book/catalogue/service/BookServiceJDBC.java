package com.whoisacat.edu.book.catalogue.service;

import com.whoisacat.edu.book.catalogue.dao.BookDao;
import com.whoisacat.edu.book.catalogue.domain.Author;
import com.whoisacat.edu.book.catalogue.domain.Book;
import com.whoisacat.edu.book.catalogue.domain.Genre;
import com.whoisacat.edu.book.catalogue.service.exception.WHORequestClientException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceJDBC implements BookService{

    private final BookDao dao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceJDBC(BookDao dao,AuthorService authorService,
                           GenreService genreService){
        this.dao = dao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override public List<Book> findAll(){
        return dao.getAll();
    }

    @Override public String addBook(String bookString,String authorString,String genreString){
        Author author = authorService.findByNameOrCreate(authorString);
        Genre genre = genreService.findByNameOrCreate(genreString);
        List<Book> existedBooks = dao.findByNameAndAuthorIdAndGenreId(bookString,author.getId(),genre.getId());
        if(!existedBooks.isEmpty()){
            throw new WHORequestClientException("Такая книга уже есть, найдены книги - "
                    .concat(buildBooksString(existedBooks)));
        }
        Book book = new Book(null,bookString,author,genre);
        dao.insert(book);
        return new StringBuilder(book.getName()).append(" ")
                .append(book.getAuthor().getName()).append(" ")
                .append(book.getGenre().getName()).toString();
    }

    @Override public long getBooksCount(){
        return dao.countAll();
    }

    @Override public List<Book> findByAuthorId(long id){
        return dao.getByAuthor(id);
    }

    private String buildBooksString(List<Book> existedBooks){
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
