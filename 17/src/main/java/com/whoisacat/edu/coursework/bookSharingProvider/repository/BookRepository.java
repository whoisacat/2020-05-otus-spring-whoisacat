package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends PagingAndSortingRepository<Book,Long>, BookCustomRepository {

    long count();

    Book getById(long id);

    List<Book> getAllByTitleContains(String title);

    List<Book> getByAuthorId(long authorId);

    @EntityGraph(attributePaths = {"author","genre"})
    @BatchSize(size = 2)
    List<Book> getAllBy(Pageable pageable);

    @EntityGraph(attributePaths = {"author","genre"})
    @BatchSize(size = 2)
    List<Book> getAllBy();

    @EntityGraph(attributePaths = {"author","genre"})
    @BatchSize(size = 2)
    Optional<Book> findById(long id);

    void deleteById(long id);

    int deleteByTitle(String title);

    @EntityGraph(attributePaths = {"author","genre"})
    List<Book> findByTitleContainsAndAuthorIdAndGenreId(String bookString,long authorId,long genreId);
}
