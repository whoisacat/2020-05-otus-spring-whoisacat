package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author,Long>{

    long count();

    Author getById(long id);

    List<Author> getByTitle(String title);

    List<Author> getAllBy();

    void deleteById(long id);
}
