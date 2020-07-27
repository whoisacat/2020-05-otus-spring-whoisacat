package com.whoisacat.edu.book.mongodb.catalogue.repository;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookMongoRepository extends MongoRepository<Book,String>{

    long count();

    Book getById(String id);

    List<Book> getAllByTitleContains(String title);

    List<Book> getByAuthor_Title(String authorTitle);

    List<Book> getAllBy();

    int deleteByTitle(String title);

    List<Book> findByTitleContainsAndAuthorTitleLikeAndGenreTitleLike(String bookString,String authorTitle,String genreTitle);

    List<Book> getBooksByTitleLike(String title);

    Optional<Book> findByTitleLikeAndAuthorTitleLikeAndGenreTitleLike(String title,String authorTitle,String genreTitle);
}
