package com.whoisacat.edu.book.mongodb.catalogue.service;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Author;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Book;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Comment;
import com.whoisacat.edu.book.mongodb.catalogue.repository.CommentMongoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceSimple implements CommentService{

    private final CommentMongoRepository repository;
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;

    public CommentServiceSimple(CommentMongoRepository repository,
                                AuthorService authorService,
                                BookService bookService,
                                GenreService genreService){
        this.repository = repository;
        this.authorService = authorService;
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @Override
    public List<String> findBookCommentsByBookTitle(String bookTitle,Integer page,Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        List<Book> books = bookService.findBooksWithSubstringInTitle(bookTitle);
        return convertCommentsListToStringList(repository.getAllByBookIn(books,pageable));
    }

    @Override
    public List<String> findBookCommentsByBookTitle(String bookTitle){
        List<Book> books = bookService.findBooksWithSubstringInTitle(bookTitle);
        return convertCommentsListToStringList(repository.getAllByBookIn(books));
    }

    @Override
    public List<String> findBookCommentsByBookAuthorTitle(String authorTitle,Integer page,Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        List<Author> authors = authorService.findAuthorsWithSubstringInTitle(authorTitle);
        return convertCommentsListToStringList(repository.getAllByBookAuthorIn(authors,pageable));
    }

    @Override
    public List<String> findBookCommentsByBookAuthorTitle(String authorTitle){
        List<Author> authors = authorService.findAuthorsWithSubstringInTitle(authorTitle);
        return convertCommentsListToStringList(repository.getAllByBookAuthorIn(authors));
    }

    @Override
    public void addComment(String title,String text,String bookTitle,String bookAuthor,String bookGenre){
        Book book = bookService.findByOwnAndAuthorAndGenreTitles(bookTitle,bookAuthor,bookGenre);
        Comment comment = new Comment(title,text,book);
        repository.save(comment);
    }

    private List<String> convertCommentsListToStringList(List<Comment> allByBookIn){
        return allByBookIn
                .stream()
                .map(Comment::toString)
                .collect(Collectors.toList());
    }
}
