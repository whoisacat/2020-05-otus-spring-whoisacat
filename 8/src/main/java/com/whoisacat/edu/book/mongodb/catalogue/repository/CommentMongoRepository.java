package com.whoisacat.edu.book.mongodb.catalogue.repository;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Author;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Book;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentMongoRepository extends MongoRepository<Comment,String>{

    long count();

    List<Comment> getAllByBook(Book book,Pageable pageable);

    List<Comment> getAllByBookIn(List<Book> book);

    List<Comment> getAllByBookIn(List<Book> book,Pageable pageable);

    List<Comment> getAllByTitleContains(String title);

    List<Comment> getAllByTextContainingIgnoreCase(String title);

    int deleteByTitle(String title);

    int deleteCommentsByBook_Author(Author author);

    List<Comment> getAllByBookAuthor(Author a,Pageable pageable);

    List<Comment> getAllByBookAuthorIn(List<Author> authors);

    List<Comment> getAllByBookAuthorIn(List<Author> authors,Pageable pageable);
}
