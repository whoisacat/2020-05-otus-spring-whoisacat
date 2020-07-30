package com.whoisacat.edu.book.springdata.catalogue.repository;

import com.whoisacat.edu.book.springdata.catalogue.domain.Book;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long>{

    long count();

    Book getById(long id);

    List<Book> getAllByTitleContains(String title);

    List<Book> getByAuthorId(long authorId);

    @EntityGraph(attributePaths = {"author","genre"})
    @BatchSize(size = 2)
    List<Book> getAllBy();

    void deleteById(long id);

    int deleteByTitle(String title);

    @EntityGraph(attributePaths = {"author","genre"})
    List<Book> findByTitleContainsAndAuthorIdAndGenreId(String bookString,long authorId,long genreId);
}
