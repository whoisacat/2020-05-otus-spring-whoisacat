package com.whoisacat.edu.book.mongodb.catalogue.repository;

import com.whoisacat.edu.book.mongodb.catalogue.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Dao для работы с жанрами должно")
@DataMongoTest
@ComponentScan({"com.whoisacat.edu.book.mongodb.catalogue.mongock",
        "com.whoisacat.edu.book.mongodb.catalogue.repositories"})
class GenreMongoRepositoryTest{

    public static final String NEW_GENRE = "Новый жанр";
    public static final String PROGRAMMING = "Программирование";
    @Autowired GenreMongoRepository repository;

    @DisplayName("насчитать два жанра")
    @Test
    void countGenre(){
        assertThat(repository.count()).isEqualTo(3L);
    }

    @DisplayName("вставить новый жанр")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertGenre(){
        Genre genre = new Genre(NEW_GENRE);
        assertThat(repository.save(genre).getTitle()).isEqualTo(NEW_GENRE);
        assertThat(repository.count()).isEqualTo(4L);
    }

    @DisplayName(value = "Найти жанр по идентефикатору")
    @Test
    void getGenreById(){
        Genre actual = repository.getById(repository.getByTitleContains(PROGRAMMING).get(0).getId());
        assertThat(actual.getTitle()).isEqualTo(PROGRAMMING);
    }

    @DisplayName(value = "Найти жанр по названию")
    @Test
    void getGenreByName(){
        List<Genre> actuals = repository.getByTitleContains(PROGRAMMING);
        assertThat(actuals.size()).isEqualTo(1);
        Genre actual = actuals.get(0);
        assertThat(actual.getTitle()).isEqualTo(PROGRAMMING);
    }

    @DisplayName(value = "Найти все жанры")
    @Test
    void getAllGenres(){
        List<Genre> actuals = repository.getAllBy();
        assertThat(actuals.size()).isEqualTo(3);
        Genre actual = actuals.get(0);
        assertThat(actual.getTitle()).isEqualTo("Программирование");
    }

    @DisplayName(value = "Удалить жанр по названию")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void deleteGenreById(){
        long count = repository.count();
        repository.deleteByTitle(PROGRAMMING);
        assertThat(repository.count()).isEqualTo(count - 1);
    }
}
