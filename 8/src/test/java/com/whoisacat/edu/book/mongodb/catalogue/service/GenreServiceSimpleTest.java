package com.whoisacat.edu.book.mongodb.catalogue.service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Genre;
import com.whoisacat.edu.book.mongodb.catalogue.service.exception.WHORequestClientException;
import com.whoisacat.edu.book.mongodb.catalogue.repository.GenreMongoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с жанрами должен")
@Import(GenreServiceSimple.class)
@DataMongoTest
class GenreServiceSimpleTest{

    private static final Genre GENRE_ODIN = new Genre("1","godin");
    private static final Genre GENRE_SECONDO = new Genre("2","second one");

    @Autowired GenreServiceSimple service;

    @MockBean GenreMongoRepository repository;

    @DisplayName(value = "Вернуть что нашел, если нашел только одного")
    @Test
    void foundByNameOneAuthor(){
        when(repository.getByTitleContains("name")).thenReturn(Lists.newArrayList(GENRE_ODIN));
        assertThat(service.findByNameOrCreate("name")).isEqualTo(GENRE_ODIN);
    }

    @DisplayName(value = "Выбросить исключение, если нашел больше одного")
    @Test
    void findByNameOrCreate(){
        when(repository.getByTitleContains("name")).thenReturn(Lists.newArrayList(GENRE_ODIN,GENRE_SECONDO));
        assertThrows(WHORequestClientException.class,() -> service.findByNameOrCreate("name"));
    }

    @DisplayName(value = "Передать количество от дао")
    @Test
    void getGenresCount(){
        when(repository.count()).thenReturn(42L);
        assertThat(service.getGenresCount()).isEqualTo(42);
    }

    @DisplayName(value = "Вернуть все жанры в строке")
    @Test
    void getAllGenresString(){
        when(repository.getAllBy()).thenReturn(Lists.newArrayList(GENRE_ODIN,GENRE_SECONDO));
        assertThat(service.getAllGenresString()).isEqualTo("godin; second one; ");
    }

    @DisplayName(value = "Вернуть строку со всеми переданными от дао жанрами в ней")
    @Test
    void findByName(){
        when(repository.getByTitleContains("name")).thenReturn(Lists.newArrayList(GENRE_SECONDO,GENRE_ODIN));
        assertThat(service.findByName("name")).isEqualTo("second one; godin; ");
    }
}
