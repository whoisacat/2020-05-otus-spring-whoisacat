package com.whoisacat.edu.book.catalogue.service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.catalogue.dao.BookDaoJdbc;
import com.whoisacat.edu.book.catalogue.domain.Author;
import com.whoisacat.edu.book.catalogue.domain.Book;
import com.whoisacat.edu.book.catalogue.domain.Genre;
import com.whoisacat.edu.book.catalogue.service.exception.WHORequestClientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами должен")
@JdbcTest
@ExtendWith(SpringExtension.class)
@Import(BookServiceJDBC.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookServiceJDBCTest{

    private static final Author AUTHOR_ODIN = new Author(1,"odin",new ArrayList<>());
    private static final Genre GENRE_OGIN = new Genre(1,"ogin");
    private static final Book BOOK_ODIN = new Book(1,"bodin",AUTHOR_ODIN,GENRE_OGIN);
    private static final String GENRE_STRING = "genreString";
    private static final String AUTHOR_STRING = "authorString";

    @Autowired
    private BookServiceJDBC service;

    @MockBean private BookDaoJdbc dao;
    @MockBean private AuthorService authorService;
    @MockBean private GenreService genreService;

    @DisplayName(value = "Должен вернуть то же что и дао")
    @Test
    void findAll(){
        when(dao.getAll()).thenReturn(Lists.newArrayList(BOOK_ODIN));
        assertThat(service.findAll()).isEqualTo(Lists.newArrayList(BOOK_ODIN));
    }

    @DisplayName(value = "Должен выбросить исключение если есть уже книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void dontAddBookWhenBookLikeThisAlreadyExists(){
        when(authorService.findByNameOrCreate(AUTHOR_STRING))
                .thenReturn(AUTHOR_ODIN);
        when(genreService.findByNameOrCreate(GENRE_STRING)).thenReturn(GENRE_OGIN);
        when(dao.findByNameAndAuthorIdAndGenreId("name",1,1))
                .thenReturn(Lists.newArrayList(BOOK_ODIN));
        assertThrows(WHORequestClientException.class,()->service.addBook("name",AUTHOR_STRING,GENRE_STRING));
    }

    @DisplayName(value = "Должен выбросить исключение если есть уже книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void addBook(){
        when(authorService.findByNameOrCreate(AUTHOR_STRING))
                .thenReturn(AUTHOR_ODIN);
        when(genreService.findByNameOrCreate(GENRE_STRING)).thenReturn(GENRE_OGIN);
        when(dao.findByNameAndAuthorIdAndGenreId("name",1,1))
                .thenReturn(Lists.newArrayList(BOOK_ODIN));
        assertThrows(WHORequestClientException.class,()->service.addBook("name",AUTHOR_STRING,GENRE_STRING));
    }

    @DisplayName(value = "Должен передать что ему сказало дао")
    @Test
    void getBooksCount(){
        when(dao.countAll()).thenReturn(5432);
        assertThat(service.getBooksCount()).isEqualTo(5432);
    }

    @DisplayName(value = "Должен передать что ему сказало дао")
    @Test
    void findByAuthorId(){
        when(dao.getByAuthor(707)).thenReturn(Lists.newArrayList(BOOK_ODIN));
        assertThat(service.findByAuthorId(707)).isEqualTo(Lists.newArrayList(BOOK_ODIN));
    }
}
