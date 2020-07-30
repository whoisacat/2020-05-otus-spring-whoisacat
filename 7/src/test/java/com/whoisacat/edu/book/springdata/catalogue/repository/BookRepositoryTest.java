package com.whoisacat.edu.book.springdata.catalogue.repository;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.springdata.catalogue.domain.Author;
import com.whoisacat.edu.book.springdata.catalogue.domain.Book;
import com.whoisacat.edu.book.springdata.catalogue.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами должно")
@DataJpaTest
@ExtendWith(SpringExtension.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookRepositoryTest{

    @Autowired BookRepository repository;
    private static final Author ROBERT_MARTIN = new Author(3L,"Роберт Мартин");;
    private static final Genre PROGRAMMING = new Genre(1L,"Программирование");
    private static final Book CLEAN_CODE = new Book(2L,"Чистый код",ROBERT_MARTIN,PROGRAMMING);;

    @DisplayName("Правильно насчитать шесть книг")
    @Test
    void count(){
        assertThat(6).isEqualTo(repository.count());
    }

    @Transactional
    @DisplayName("Вставить книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insert(){
        assertThat(repository.save(new Book(null,"Чистая архитектура",
                new Author(3L,""),
                new Genre(1L,""))).getId()).isEqualTo(repository.count());
    }

    @Transactional
    @DisplayName("Вставить книгу с правильным автором")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertWithCorrectAuthorName(){
        assertThat(7L).isEqualTo(repository.save(new Book(null,"Чистая архитектура",
                new Author(3L,"Роберт Мартин"),
                new Genre(1L,"Программирование"))).getId());
        assertThat(repository.getById(repository.count()).getAuthor().getTitle()).isEqualTo("Роберт Мартин");
    }

    @Transactional
    @DisplayName("Вставить книгу с правильным названием")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertWithCorrectName(){
        Book book = repository.save(new Book(null,"Чистая архитектура",
                new Author(3L,""),
                new Genre(1L,"")));
        assertThat(7L).isEqualTo(book.getId());
        assertThat(book.getTitle()).isEqualTo("Чистая архитектура");
    }

    @Transactional
    @DisplayName("Найти книгу по идентефикатору")
    @Test
    void getById(){
        Book actual = repository.getById(2);
        assertThat(CLEAN_CODE.getId()).isEqualTo(actual.getId());
        assertThat(CLEAN_CODE.getTitle()).isEqualTo(actual.getTitle());
        assertThat(CLEAN_CODE.getAuthor().getId()).isEqualTo(actual.getAuthor().getId());
        assertThat(CLEAN_CODE.getAuthor().getTitle()).isEqualTo(actual.getAuthor().getTitle());
        assertThat(CLEAN_CODE.getGenre().getId()).isEqualTo(actual.getGenre().getId());
        assertThat(CLEAN_CODE.getGenre().getTitle()).isEqualTo(actual.getGenre().getTitle());
    }

    @Transactional
    @DisplayName("Найти книгу по названию")
    @Test
    void getByName(){
        List<Book> actualList = repository.getAllByTitleContains("Чистый");
        assertThat(1).isEqualTo(actualList.size());
        Book actual = actualList.get(0);
        assertThat(CLEAN_CODE.getId()).isEqualTo(actual.getId());
        assertThat(CLEAN_CODE.getTitle()).isEqualTo(actual.getTitle());
        assertThat(CLEAN_CODE.getAuthor().getId()).isEqualTo(actual.getAuthor().getId());
        assertThat(CLEAN_CODE.getAuthor().getTitle()).isEqualTo(actual.getAuthor().getTitle());
        assertThat(CLEAN_CODE.getGenre().getId()).isEqualTo(actual.getGenre().getId());
        assertThat(CLEAN_CODE.getGenre().getTitle()).isEqualTo(actual.getGenre().getTitle());
    }

    @Transactional
    @DisplayName("Найти книгу по автору")
    @Test
    void getByAuthor(){

        List<Book> actual = repository.getByAuthorId(3);

        Author author = new Author(3L,"Роберт Мартин");
        Genre genre = new Genre(1L,"Программирование");
        Book expected2 = new Book(3L,"Идевльный программист",author,genre);
        List<Book> expected = Lists.newArrayList(CLEAN_CODE,expected2);
        expected.sort(Comparator.comparing(Book::getId));
        actual.sort(Comparator.comparing(Book::getId));
        assertThat(expected.size()).isEqualTo(actual.size());
        assertThat(expected.get(0).getId() == actual.get(0).getId());
        assertThat(expected.get(1).getId() == actual.get(1).getId());
        assertThat(expected.get(0).getTitle() == actual.get(0).getTitle());
        assertThat(expected.get(1).getTitle() == actual.get(1).getTitle());
        assertThat(expected.get(0).getAuthor().getId() == actual.get(0).getAuthor().getId());
        assertThat(expected.get(1).getAuthor().getId() == actual.get(1).getAuthor().getId());
        assertThat(expected.get(0).getAuthor().getTitle() == actual.get(0).getAuthor().getTitle());
        assertThat(expected.get(1).getAuthor().getTitle() == actual.get(1).getAuthor().getTitle());
        assertThat(expected.get(0).getGenre().getId() == actual.get(0).getGenre().getId());
        assertThat(expected.get(1).getGenre().getId() == actual.get(1).getGenre().getId());
        assertThat(expected.get(0).getGenre().getTitle() == actual.get(0).getGenre().getTitle());
        assertThat(expected.get(1).getGenre().getTitle() == actual.get(1).getGenre().getTitle());
    }

    @DisplayName(value = "Вернуть список из всех книг")
    @Test
    void getAll(){
        assertThat(repository.getAllBy().size()).isEqualTo(6);
    }

    @Transactional
    @DisplayName(value = "Удалить книгу по идентификатору")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteById(){
        long count = repository.count();
        repository.deleteById(1);
        assertThat(count - 1).isEqualTo(repository.count());
        assertThat(repository.getById(1)).isNull();
    }

    @Transactional
    @DisplayName(value = "Удалить книгу по имени")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteByName(){
        long count = repository.count();
        assertThat(1).isEqualTo(repository.deleteByTitle("Исскуство войны"));
        assertThat(count - 1).isEqualTo(repository.count());
        assertThat(repository.getById(1)).isNull();
    }

    @DisplayName(value = "Найти книгу по названию и идентефикаторам автора и жанра")
    @Test
    void findByNameAndAuthorIdAndGenreId(){
        List<Book> actual = repository.findByTitleContainsAndAuthorIdAndGenreId("Чистый ко",3,1);
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getTitle()).isEqualTo("Чистый код");
    }
}
