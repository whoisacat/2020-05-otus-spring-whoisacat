package com.whoisacat.edu.book.springmvcini.catalogue.service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Genre;
import com.whoisacat.edu.book.springmvcini.catalogue.repository.GenreRepository;
import com.whoisacat.edu.book.springmvcini.catalogue.service.exception.WHORequestClientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с жанрами должен")
@ExtendWith(SpringExtension.class)
@Import(GenreServiceMvc.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class GenreServiceMvcTest{

    private static final Genre GENRE_ODIN = new Genre("1L","godin");
    private static final Genre GENRE_SECONDO = new Genre("2L","second one");

    @Autowired GenreServiceMvc service;

    @MockBean GenreRepository repository;

    @DisplayName(value = "Вернуть что нашел, если нашел только одного")
    @Test
    void foundByNameOneAuthor(){
        when(repository.getByTitle("name")).thenReturn(Lists.newArrayList(GENRE_ODIN));
        assertThat(service.findByNameOrCreate("name")).isEqualTo(GENRE_ODIN);
    }

    @DisplayName(value = "Выбросить исключение, если нашел больше одного")
    @Test
    void findByNameOrCreate(){
        when(repository.getByTitle("name")).thenReturn(Lists.newArrayList(GENRE_ODIN,GENRE_SECONDO));
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
        when(repository.getByTitle("name")).thenReturn(Lists.newArrayList(GENRE_SECONDO,GENRE_ODIN));
        assertThat(service.findByName("name")).isEqualTo("second one; godin; ");
    }
}
