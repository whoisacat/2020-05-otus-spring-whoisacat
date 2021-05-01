package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Author;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Genre;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.BookRepository;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHOBookAlreadyExists;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHODataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    @Override
    @HystrixCommand(commandKey = "findAll", fallbackMethod = "buildFallbackRows")
    public Page<Book> findAll(Pageable pageable){
        return new PageImpl<>(repository.getAllBy(pageable),pageable,repository.count());
    }

    @Transactional
    @Override
    @HystrixCommand(commandKey = "addBook", fallbackMethod = "buildFallbackRows")
    public Book addBook(String bookString,String authorString,String genreString){
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

    @Override
    @HystrixCommand(commandKey = "getBooksCount", fallbackMethod = "buildFallbackRows")
    public long getBooksCount(){
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    @HystrixCommand(commandKey = "findByAuthorId", fallbackMethod = "buildFallbackRows")
    public List<Book> findByAuthorId(long id){
        return repository.getByAuthorId(id);
    }

    @Override
    @HystrixCommand(commandKey = "buildBooksString", fallbackMethod = "buildFallbackRows")
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
    @HystrixCommand(commandKey = "findById", fallbackMethod = "buildFallbackRows")
    public Optional<Book> findById(long id){
        return repository.findById(id);
    }

    @Override
    @HystrixCommand(commandKey = "update", fallbackMethod = "buildFallbackRows")
    public Book update(BookDTO dto){
        Author author = new Author(dto.getAuthorId(),dto.getAuthorTitle());
        author = authorService.update(author);
        Genre genre = new Genre(dto.getGenreId(),dto.getGenreTitle());
        genre = genreService.update(genre);

        Book book = new Book(dto.getId(),dto.getTitle(),author,genre);
        return repository.save(book);
    }

    @Override
    @HystrixCommand(commandKey = "delete", fallbackMethod = "buildFallbackRows")
    public void delete(long id){
        repository.deleteById(id);
    }

    public void buildFallbackRows() {
        throw new WHODataAccessException("Database is not available");
    }
}
