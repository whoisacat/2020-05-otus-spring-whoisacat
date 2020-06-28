package com.whoisacat.edu.book.catalogue.dao;

import com.whoisacat.edu.book.catalogue.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@ExtendWith(SpringExtension.class)
@Import(AuthorDaoJdbc.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class AuthorDaoJdbcTest{
    @Autowired
    private AuthorDaoJdbc dao;

    @DisplayName(value = "правильно посчитать авторов")
    @Test
    void countAuthorsTest(){
        assertThat(dao.count()).isEqualTo(6);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "вставить нового автора")
    @Test
    void insertAuthorsTest(){
        assertThat(6).isEqualTo(dao.count());
        dao.insert(new Author(dao.count() + 1,"some new author",new ArrayList<>()));
        assertThat(dao.count()).isEqualTo(7);
    }

    @DisplayName(value = "найти одного автора по идентефикатору")
    @Test
    void getAuthorByIdTest(){
        Author author = dao.getById(3);
        assertThat(author.getId()).isEqualTo(3);
    }

    @DisplayName(value = "найти автора по идентефикатору")
    @Test
    void mapAuthorsNameByIdTest(){
        Author author = dao.getById(3);
        assertThat(author.getName()).isEqualTo("Роберт Мартин");
    }

    @DisplayName(value = "найти двух авторов по имени")
    @Test
    void getAuthorByNameTest(){
        List<Author> authors = dao.getByName("Роберт Мартин");
        assertThat(authors.size() == 2);
    }

    @DisplayName(value = "найти всех авторов")
    @Test
    void getAllAuthorsTest(){
        List<Author> list = dao.getAll();
        assertThat(list.size() == 6);
        list.sort(Comparator.comparing(Author::getId));
        assertThat(list.get(2).getName()).isEqualTo("Роберт Мартин");
    }

    @DisplayName(value = "выбросить исключение так как есть связи")
    @Test
    void noDeletingAuthorByIdTest(){
        assertThrows(DataIntegrityViolationException.class,() -> dao.deleteById(1));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "удалить автора по идентефикатору")
    @Test
    void deleteAuthorByIdTest(){
        dao.deleteById(6);
        assertThat(5).isEqualTo(dao.count());
    }
}
