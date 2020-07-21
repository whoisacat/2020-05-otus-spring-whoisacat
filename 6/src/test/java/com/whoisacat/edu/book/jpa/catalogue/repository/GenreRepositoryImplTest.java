package com.whoisacat.edu.book.jpa.catalogue.repository;

import com.whoisacat.edu.book.jpa.catalogue.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с жанрами должен")
@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(GenreRepositoryImpl.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class GenreRepositoryImplTest{

    public static final long INITIAL_GENRES_COUNT = 3L;
    public static final String NEW_GENRE_TITLE = "Новый жанр";
    public static final long NEW_GENRE_ID = 4L;
    public static final String PROGRAMMING_TITLE = "Программирование";
    public static final int PROGRAMMING_ID = 1;
    public static final int INITIAL_INDEX = 0;
    @Autowired GenreRepositoryImpl repository;

    @Transactional
    @DisplayName("насчитать три жанра")
    @Test
    void countGenre(){
        assertThat(repository.count()).isEqualTo(INITIAL_GENRES_COUNT);
    }

    @Transactional
    @DisplayName("вставить новый жанр")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertGenre(){
        Genre genre = new Genre(null,NEW_GENRE_TITLE);
        assertThat(repository.save(genre).getId()).isEqualTo(NEW_GENRE_ID);
        assertThat(repository.count()).isEqualTo(INITIAL_GENRES_COUNT + 1);
    }

    @Transactional
    @DisplayName("не вставить стырый жанр")
    @Test
    void dontInsertGenre(){
        long count = repository.count();
        Genre genre = new Genre(null,PROGRAMMING_TITLE);
        assertThat(repository.save(genre)).isNull();
        assertThat(repository.count()).isEqualTo(count);
    }

    @DisplayName(value = "Найти жанр по идентефикатору")
    @Test
    void getGenreById(){
        Genre actual = repository.getById(PROGRAMMING_ID);
        assertThat(actual.getId()).isEqualTo(PROGRAMMING_ID);
        assertThat(actual.getTitle()).isEqualTo(PROGRAMMING_TITLE);
    }

    @DisplayName(value = "Найти жанр по названию")
    @Test
    void getGenreByName(){
        List<Genre> actuals = repository.getByName(PROGRAMMING_TITLE);
        assertThat(actuals.size()).isEqualTo(PROGRAMMING_ID);
        Genre actual = actuals.get(INITIAL_INDEX);
        assertThat(actual.getId()).isEqualTo(PROGRAMMING_ID);
        assertThat(actual.getTitle()).isEqualTo(PROGRAMMING_TITLE);
    }

    @DisplayName(value = "Найти все жанры")
    @Test
    void getAllGenres(){
        List<Genre> actuals = repository.getAll();
        assertThat(actuals.size()).isEqualTo(INITIAL_GENRES_COUNT);
        Genre actual = actuals.get(INITIAL_INDEX);
        assertThat(actual.getId()).isEqualTo(PROGRAMMING_ID);
        assertThat(actual.getTitle()).isEqualTo(PROGRAMMING_TITLE);
    }

    @Transactional
    @DisplayName(value = "Удалить жанр по идентефикатору")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void deleteGenreById(){
        long count = repository.count();
        repository.deleteById(INITIAL_GENRES_COUNT);
        assertThat(repository.count()).isEqualTo(count - 1);
    }
}
