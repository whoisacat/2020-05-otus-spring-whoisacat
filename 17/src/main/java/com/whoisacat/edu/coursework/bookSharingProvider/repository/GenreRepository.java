package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre,Long>{

    long count();

    Genre getById(long id);

    List<Genre> getByTitle(String title);

    List<Genre> getAllBy();

    void deleteById(long id);
}
