package com.whoisacat.edu.book.jdbc.catalogue.dao;

import com.whoisacat.edu.book.jdbc.catalogue.domain.Genre;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@ExtendWith(SpringExtension.class)
@Import(GenreDaoJdbc.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class GenreDaoJdbcTest{

    @Autowired GenreDaoJdbc dao;

    @DisplayName("насчитать два жанра")
    @Test
    void countGenre(){
        assertThat(dao.count()).isEqualTo(3);
    }

    @DisplayName("вставить новый жанр")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertGenre(){
        Genre genre = new Genre(null,"Новый жанр");
        assertThat(dao.insert(genre)).isEqualTo(4L);
        assertThat(dao.count()).isEqualTo(4L);
    }

    @DisplayName("не вставить стырый жанр")
    @Test
    void dontInsertGenre(){
        long count = dao.count();
        Genre genre = new Genre(null,"Программирование");
        assertThat(dao.insert(genre)).isNull();
        assertThat(dao.count()).isEqualTo(count);
    }

    @DisplayName(value = "Найти жанр по идентефикатору")
    @Test
    void getGenreById(){
        Genre actual = dao.getById(1);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getName()).isEqualTo("Программирование");
    }

    @DisplayName(value = "Найти жанр по названию")
    @Test
    void getGenreByName(){
        List<Genre> actuals = dao.getByName("Программирование");
        assertThat(actuals.size()).isEqualTo(1);
        Genre actual = actuals.get(0);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getName()).isEqualTo("Программирование");
    }

    @DisplayName(value = "Найти все жанры")
    @Test
    void getAllGenres(){
        List<Genre> actuals = dao.getAll();
        assertThat(actuals.size()).isEqualTo(3);
        Genre actual = actuals.get(0);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getName()).isEqualTo("Программирование");
    }

    @DisplayName(value = "Удалить жанр по идентефикатору")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void deleteGenreById(){
        long count = dao.count();
        dao.deleteById(3);
        assertThat(dao.count()).isEqualTo(count - 1);
    }
}
