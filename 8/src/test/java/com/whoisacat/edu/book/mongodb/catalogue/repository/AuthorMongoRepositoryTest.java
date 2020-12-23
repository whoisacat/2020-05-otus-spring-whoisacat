package com.whoisacat.edu.book.mongodb.catalogue.repository;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами должно")
@DataMongoTest
@ComponentScan({"com.whoisacat.edu.book.mongodb.catalogue.mongock",
        "com.whoisacat.edu.book.mongodb.catalogue.repositories"})
class AuthorMongoRepositoryTest{

    public static final String UNCLE_BOB_TITLE = "Роберт Мартин";
    public static final String UNCLE_BOB_FIRST_NAME = "Роберт";
    public static final String DMITRY = "Дмитрий";
    public static final String BYKOV = "Быков";
    public static final String SOME_NEW_AUTHOR = "some new author";
    public static final String BYKO = "Быко";
    @Autowired private AuthorMongoRepository repository;

    @DisplayName(value = "правильно посчитать авторов")
    @Test
    void countAuthorsTest(){
        assertThat(repository.count()).isEqualTo(6);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "вставить нового автора")
    @Test
    void insertAuthorsTest(){
        assertThat(repository.count()).isEqualTo(6);
        repository.save(new Author(SOME_NEW_AUTHOR));
        assertThat(repository.count()).isEqualTo(7);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "вставить нового автора после удаления автора")
    @Test
    void insertAuthorAfterDeletingAuthorAfterInsertingAuthorTest(){
        assertThat(repository.count()).isEqualTo(6);
        repository.save(new Author(SOME_NEW_AUTHOR));
        repository.save(new Author(SOME_NEW_AUTHOR));
        assertThat(repository.count()).isEqualTo(8);
        repository.deleteById(repository.getByTitleContains(BYKO).get(0).getId());
        assertThat(repository.count()).isEqualTo(7);
        repository.save(new Author(SOME_NEW_AUTHOR));
        assertThat(repository.count()).isEqualTo(8);
    }

    @DisplayName(value = "найти одного автора по идентефикатору")
    @Test
    void getAuthorByIdTest(){
        Author author = repository.getById(repository.getByTitleContains(UNCLE_BOB_FIRST_NAME).get(0).getId());
        assertThat(author.getTitle()).isEqualTo(UNCLE_BOB_TITLE);
    }

    @DisplayName(value = "найти автора по идентефикатору")
    @Test
    void mapAuthorsNameByIdTest(){
        Author author = repository.getById(repository.getByTitleContains(UNCLE_BOB_FIRST_NAME).get(0).getId());
        assertThat(author.getTitle()).isEqualTo(UNCLE_BOB_TITLE);
    }

    @DisplayName(value = "найти двух авторов по имени")
    @Test
    void getAuthorByNameTest(){
        List<Author> authors = repository.getByTitleContains(DMITRY);
        assertThat(authors.size()).isEqualTo(2);
        authors.forEach(System.out::println);
    }

    @DisplayName(value = "найти всех авторов")
    @Test
    void getAllAuthorsTest(){
        List<Author> list = repository.getAllBy();
        assertThat(list.size() == 6);
        list.sort(Comparator.comparing(Author::getId));
        assertThat(list.get(2).getTitle()).isEqualTo(UNCLE_BOB_TITLE);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "удалить автора по идентефикатору")
    @Test
    void deleteAuthorByIdTest(){
        assertThat(6L).isEqualTo(repository.count());
        repository.deleteById(repository.getByTitleContains(BYKOV).get(0).getId());
        assertThat(5L).isEqualTo(repository.count());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "удалить автора по идентефикатору")
    @Test
    void deleteAuthorByTitleTest(){
        assertThat(6L).isEqualTo(repository.count());
        repository.deleteAuthorByTitleLike(BYKOV);
        assertThat(5L).isEqualTo(repository.count());
    }
}
