package com.whoisacat.edu.book.springdata.catalogue.repository;

import com.whoisacat.edu.book.springdata.catalogue.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с авторами должно")
@DataJpaTest
@ExtendWith(SpringExtension.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class AuthorRepositoryTest{
    @Autowired
    private AuthorRepository repository;

    @DisplayName(value = "правильно посчитать авторов")
    @Test
    void countAuthorsTest(){
        assertThat(repository.count()).isEqualTo(6);
    }

    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "вставить нового автора")
    @Test
    void insertAuthorsTest(){
        assertThat(6L).isEqualTo(repository.count());
        repository.save(new Author(null,"some new author"));
        assertThat(repository.count()).isEqualTo(7);
    }

    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "вставить нового автора после удаления автора")
    @Test
    void insertAuthorAfterDeletingAuthorAfterInsertingAuthorTest(){
        repository.save(new Author(null,"some new author"));
        repository.save(new Author(null,"some new author"));
        assertThat(repository.count()).isEqualTo(8);
        repository.deleteById(6);
        assertThat(repository.count()).isEqualTo(7);
        repository.save(new Author(null,"some new author"));
        assertThat(repository.count()).isEqualTo(8);
    }

    @DisplayName(value = "найти одного автора по идентефикатору")
    @Test
    void getAuthorByIdTest(){
        Author author = repository.getById(3);
        assertThat(author.getId()).isEqualTo(3);
    }

    @DisplayName(value = "найти автора по идентефикатору")
    @Test
    void mapAuthorsNameByIdTest(){
        Author author = repository.getById(3);
        assertThat(author.getTitle()).isEqualTo("Роберт Мартин");
    }

    @DisplayName(value = "найти двух авторов по имени")
    @Test
    void getAuthorByNameTest(){
        List<Author> authors = repository.getByTitle("Роберт Мартин");
        assertThat(authors.size() == 2);
    }

    @DisplayName(value = "найти всех авторов")
    @Test
    void getAllAuthorsTest(){
        List<Author> list = repository.getAllBy();
        assertThat(list.size() == 6);
        list.sort(Comparator.comparing(Author::getId));
        assertThat(list.get(2).getTitle()).isEqualTo("Роберт Мартин");
    }

    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "удалить автора по идентефикатору")
    @Test
    void deleteAuthorByIdTest(){
        assertThat(6L).isEqualTo(repository.count());
        repository.deleteById(6);
        assertThat(5L).isEqualTo(repository.count());
    }
}
