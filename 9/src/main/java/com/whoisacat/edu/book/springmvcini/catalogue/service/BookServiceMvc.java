package com.whoisacat.edu.book.springmvcini.catalogue.service;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Author;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Genre;
import com.whoisacat.edu.book.springmvcini.catalogue.dto.BookDTO;
import com.whoisacat.edu.book.springmvcini.catalogue.repository.BookRepository;

import com.whoisacat.edu.book.springmvcini.catalogue.service.exception.WHOBookAlreadyExists;
import com.whoisacat.edu.book.springmvcini.catalogue.service.exception.WHODataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceMvc implements BookService{

    private final BookRepository repository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceMvc(BookRepository repository,AuthorService authorService,
                          GenreService genreService){
        this.repository = repository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override public Page<Book> findAll(PageRequest pageRequest){
        return new PageImpl<>(repository.getAllBy(pageRequest),pageRequest,repository.count());
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
    @Override public List<Book> findByAuthorId(String id){
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

    @Override
    public Optional<Book> findById(String id){
        return repository.findById(id);
    }

    @Override public Book update(BookDTO dto){
        Author author = new Author(dto.getAuthorId(),dto.getAuthorTitle());
        author = authorService.update(author);
        Genre genre = new Genre(dto.getGenreId(),dto.getGenreTitle());
        genre = genreService.update(genre);

        Book book = new Book(dto.getId(),dto.getTitle(),author,genre);
        return repository.save(book);
    }

    @Override
    public void delete(String id){
        repository.deleteById(id);
    }

    @Override public List<Book> findBooksWithSubstringInTitle(String substring) {
        return repository.getAllByTitleContains(substring);
    }

    @Override public Book findByOwnAndAuthorAndGenreTitles(String title, String authorTitle, String genreTitle) {
        return repository
                .findByTitleLikeAndAuthorTitleLikeAndGenreTitleLike(title,authorTitle,genreTitle)
                .orElseThrow();

    }
}
