package com.whoisacat.edu.book.springmvcini.catalogue.service;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Author;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Comment;
import com.whoisacat.edu.book.springmvcini.catalogue.dto.CommentDTO;
import com.whoisacat.edu.book.springmvcini.catalogue.repository.CommentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceMvc implements CommentService {

    private final CommentRepository repository;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommentServiceMvc(CommentRepository repository,
                             AuthorService authorService,
                             BookService bookService){
        this.repository = repository;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public List<CommentDTO> findBookCommentsByBookTitle(String bookTitle, Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        List<Book> books = bookService.findBooksWithSubstringInTitle(bookTitle);
        return toDTO(repository.getAllByBookIn(books,pageable));
    }

    @Override
    public List<CommentDTO> findBookCommentsByBookTitle(String bookTitle){
        List<Book> books = bookService.findBooksWithSubstringInTitle(bookTitle);
        return toDTO(repository.getAllByBookIn(books));
    }

    @Override
    public List<CommentDTO> findBookCommentsByBookAuthorTitle(String authorTitle, Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        List<Author> authors = authorService.findAuthorsWithSubstringInTitle(authorTitle);
        return toDTO(repository.getAllByBookAuthorIn(authors,pageable));
    }

    @Override
    public List<CommentDTO> findBookCommentsByBookAuthorTitle(String authorTitle){
        List<Author> authors = authorService.findAuthorsWithSubstringInTitle(authorTitle);
        return toDTO(repository.getAllByBookAuthorIn(authors));
    }

    @Override
    public CommentDTO addComment(String title,String text,String bookTitle,String bookAuthor,String bookGenre){
        Book book = bookService.findByOwnAndAuthorAndGenreTitles(bookTitle,bookAuthor,bookGenre);
        Comment comment = new Comment(title,text,book);
        comment = repository.save(comment);
        return new CommentDTO(comment.getId(), comment.getTitle(), comment.getText());
    }

    private List<CommentDTO> toDTO(List<Comment> allByBookIn){
        return allByBookIn
                .stream()
                .map(c -> new CommentDTO(c.getId(), c.getTitle(), c.getText()))
                .collect(Collectors.toList());
    }
}
