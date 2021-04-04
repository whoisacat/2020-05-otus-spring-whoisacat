package com.whoisacat.edu.book.springactuatorini.catalogue.repository;

import com.whoisacat.edu.book.springactuatorini.catalogue.domain.Book;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book,Long>{

    long count();

    Book getById(long id);

    @RestResource(path = "title", rel = "title")
    List<Book> getAllByTitleContains(String title);

    @RestResource(path = "authorId", rel = "authorId")
    List<Book> getByAuthorId(long authorId);

    @EntityGraph(attributePaths = {"author","genre"})
    @BatchSize(size = 2)
    List<Book> getAllBy(Pageable pageable);

    @RestResource(path = "all", rel = "all")
    @EntityGraph(attributePaths = {"author","genre"})
    @BatchSize(size = 2)
    List<Book> getAllBy();

    @RestResource(path = "id", rel = "id")
    @EntityGraph(attributePaths = {"author","genre"})
    @BatchSize(size = 2)
    Optional<Book> findById(long id);

    void deleteById(long id);

    int deleteByTitle(String title);

    @EntityGraph(attributePaths = {"author","genre"})
    List<Book> findByTitleContainsAndAuthorIdAndGenreId(String bookString,long authorId,long genreId);
}
