package com.whoisacat.edu.book.catalogue.dao;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.catalogue.domain.Author;
import com.whoisacat.edu.book.catalogue.domain.Book;
import com.whoisacat.edu.book.catalogue.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@ExtendWith(SpringExtension.class)
@Import(BookDaoJdbc.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookDaoJdbcTest{

    @Autowired BookDaoJdbc dao;
    private static final Author ROBERT_MARTIN = new Author(3L,"Роберт Мартин",new ArrayList<>());;
    private static final Genre PROGRAMMING = new Genre(1L,"Программирование");
    private static final Book CLEAN_CODE = new Book(2,"Чистый код",ROBERT_MARTIN,PROGRAMMING);;

    @DisplayName("Правильно насчитать шесть книг")
    @Test
    void count(){
        assertThat(6).isEqualTo(dao.countAll());
    }

    @DisplayName("Вставить книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insert(){
        dao.insert(new Book(dao.countAll() + 1,"Чистая архитектура",
                new Author(3L,"",new ArrayList<>()),
                new Genre(1L,"")));
        assertThat(7).isEqualTo(dao.countAll());
    }

    @DisplayName("Вставить книгу с правильным автором")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertWithCorrectAuthorName(){
        assertThat(1).isEqualTo(dao.insert(new Book(dao.countAll() + 1,"Чистая архитектура",
                new Author(3L,"",new ArrayList<>()),
                new Genre(1L,""))));
        assertThat(dao.getById(dao.countAll()).getAuthor().getName()).isEqualTo("Роберт Мартин");
    }

    @DisplayName("Вставить книгу с правильным названием")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertWithCorrectName(){
        assertThat(1).isEqualTo(dao.insert(new Book(dao.countAll() + 1,"Чистая архитектура",
                new Author(3L,"",new ArrayList<>()),
                new Genre(1L,""))));
        assertThat(dao.getById(dao.countAll()).getName()).isEqualTo("Чистая архитектура");
    }

    @DisplayName("Найти книгу по идентефикатору")
    @Test
    void getById(){
        Book actual = dao.getById(2);
        assertThat(CLEAN_CODE.getId()).isEqualTo(actual.getId());
        assertThat(CLEAN_CODE.getName()).isEqualTo(actual.getName());
        assertThat(CLEAN_CODE.getAuthor().getId()).isEqualTo(actual.getAuthor().getId());
        assertThat(CLEAN_CODE.getAuthor().getName()).isEqualTo(actual.getAuthor().getName());
        assertThat(CLEAN_CODE.getAuthor().getBooks()).isEqualTo(actual.getAuthor().getBooks());
        assertThat(CLEAN_CODE.getGenre().getId()).isEqualTo(actual.getGenre().getId());
        assertThat(CLEAN_CODE.getGenre().getName()).isEqualTo(actual.getGenre().getName());
    }

    @DisplayName("Найти книгу по названию")
    @Test
    void getByName(){
        List<Book> actualList = dao.getByName("Чистый");
        assertThat(1).isEqualTo(actualList.size());
        Book actual = actualList.get(0);
        assertThat(CLEAN_CODE.getId()).isEqualTo(actual.getId());
        assertThat(CLEAN_CODE.getName()).isEqualTo(actual.getName());
        assertThat(CLEAN_CODE.getAuthor().getId()).isEqualTo(actual.getAuthor().getId());
        assertThat(CLEAN_CODE.getAuthor().getName()).isEqualTo(actual.getAuthor().getName());
        assertThat(CLEAN_CODE.getAuthor().getBooks()).isEqualTo(actual.getAuthor().getBooks());
        assertThat(CLEAN_CODE.getGenre().getId()).isEqualTo(actual.getGenre().getId());
        assertThat(CLEAN_CODE.getGenre().getName()).isEqualTo(actual.getGenre().getName());
    }

    @DisplayName("Найти книгу по автору")
    @Test
    void getByAuthor(){

        List<Book> actual = dao.getByAuthor(3);

        Author author = new Author(3L,"Роберт Мартин",new ArrayList<>());
        Genre genre = new Genre(1L,"Программирование");
        Book expected2 = new Book(3,"Идевльный программист",author,genre);
        List<Book> expected = Lists.newArrayList(CLEAN_CODE,expected2);
        expected.sort(Comparator.comparing(Book::getId));
        actual.sort(Comparator.comparing(Book::getId));
        assertThat(expected.size()).isEqualTo(actual.size());
        assertThat(expected.get(0).getId() == actual.get(0).getId());
        assertThat(expected.get(1).getId() == actual.get(1).getId());
        assertThat(expected.get(0).getName() == actual.get(0).getName());
        assertThat(expected.get(1).getName() == actual.get(1).getName());
        assertThat(expected.get(0).getAuthor().getId() == actual.get(0).getAuthor().getId());
        assertThat(expected.get(1).getAuthor().getId() == actual.get(1).getAuthor().getId());
        assertThat(expected.get(0).getAuthor().getName() == actual.get(0).getAuthor().getName());
        assertThat(expected.get(1).getAuthor().getName() == actual.get(1).getAuthor().getName());
        assertThat(expected.get(0).getGenre().getId() == actual.get(0).getGenre().getId());
        assertThat(expected.get(1).getGenre().getId() == actual.get(1).getGenre().getId());
        assertThat(expected.get(0).getGenre().getName() == actual.get(0).getGenre().getName());
        assertThat(expected.get(1).getGenre().getName() == actual.get(1).getGenre().getName());
    }

    @DisplayName(value = "Вернуть список из всех книг")
    @Test
    void getAll(){
        assertThat(dao.getAll().size()).isEqualTo(6);
    }

    @DisplayName(value = "Удалить книгу по идентификатору")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteById(){
        int count = dao.countAll();
        dao.deleteById(1);
        assertThat(count - 1).isEqualTo(dao.countAll());
        assertThat(dao.getById(1)).isNull();
    }

    @DisplayName(value = "Удалить книгу по имени")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteByName(){
        int count = dao.countAll();
        dao.deleteByName("Исскуство войны");
        assertThat(count - 1).isEqualTo(dao.countAll());
        assertThat(dao.getById(1)).isNull();
    }

    @DisplayName(value = "Найти книгу по названию и идентефикаторам автора и жанра")
    @Test
    void findByNameAndAuthorIdAndGenreId(){
        List<Book> actual = dao.findByNameAndAuthorIdAndGenreId("Чистый ко",3,1);
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getName()).isEqualTo("Чистый код");
    }
}
