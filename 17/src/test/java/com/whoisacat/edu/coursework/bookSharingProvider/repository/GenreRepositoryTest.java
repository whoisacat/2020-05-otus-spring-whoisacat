package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Genre;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Dao для работы с жанрами должно")
@DataJpaTest
@ExtendWith(SpringExtension.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureEmbeddedDatabase
class GenreRepositoryTest{

    @Autowired GenreRepository repository;

    @Transactional
    @DisplayName("насчитать два жанра")
    @Test
    void countGenre(){
        assertThat(repository.count()).isEqualTo(3L);
    }

    @Transactional
    @DisplayName("вставить новый жанр")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertGenre(){
        Genre genre = new Genre(null,"Новый жанр");
        assertThat(repository.save(genre).getId()).isEqualTo(4L);
        assertThat(repository.count()).isEqualTo(4L);
    }

    @DisplayName("не вставить стырый жанр")
    @Test
    void dontInsertGenre(){
        long count = repository.count();
        Genre genre = new Genre(null,"Программирование");
        assertThrows(DataIntegrityViolationException.class,()->repository.save(genre));
        assertThat(repository.count()).isEqualTo(count);
    }

    @DisplayName(value = "Найти жанр по идентефикатору")
    @Test
    void getGenreById(){
        Genre actual = repository.getById(1);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getTitle()).isEqualTo("Программирование");
    }

    @DisplayName(value = "Найти жанр по названию")
    @Test
    void getGenreByName(){
        List<Genre> actuals = repository.getByTitle("Программирование");
        assertThat(actuals.size()).isEqualTo(1);
        Genre actual = actuals.get(0);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getTitle()).isEqualTo("Программирование");
    }

    @DisplayName(value = "Найти все жанры")
    @Test
    void getAllGenres(){
        List<Genre> actuals = repository.getAllBy();
        assertThat(actuals.size()).isEqualTo(3);
        Genre actual = actuals.get(0);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getTitle()).isEqualTo("Программирование");
    }

    @Transactional
    @DisplayName(value = "Удалить жанр по идентефикатору")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void deleteGenreById(){
        long count = repository.count();
        repository.deleteById(3);
        assertThat(repository.count()).isEqualTo(count - 1);
    }
}
