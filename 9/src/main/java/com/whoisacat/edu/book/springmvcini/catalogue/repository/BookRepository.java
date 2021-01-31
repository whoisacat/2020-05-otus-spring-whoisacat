package com.whoisacat.edu.book.springmvcini.catalogue.repository;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,String>{

    long count();

    Book getById(String id);

    List<Book> getAllByTitleContains(String title);

    List<Book> getByAuthor_Title(String authorTitle);

    List<Book> getAllBy(Pageable pageable);

    List<Book> getAllBy(PageRequest pageRequest);

    int deleteByTitle(String title);

    List<Book> findByTitleContainsAndAuthorTitleLikeAndGenreTitleLike(String bookString,String authorTitle,String genreTitle);

    List<Book> getBooksByTitleLike(String title);

    Optional<Book> findByTitleLikeAndAuthorTitleLikeAndGenreTitleLike(String title,String authorTitle,String genreTitle);

    List<Book> getByAuthorId(String id);

    List<Book> findByTitleContainsAndAuthorIdAndGenreId(String bookString, String id, String id1);

    List<Book> getAllBy();
}
