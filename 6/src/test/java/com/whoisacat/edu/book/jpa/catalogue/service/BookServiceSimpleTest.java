package com.whoisacat.edu.book.jpa.catalogue.service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.jpa.catalogue.dao.BookDaoJdbc;
import com.whoisacat.edu.book.jpa.catalogue.domain.Author;
import com.whoisacat.edu.book.jpa.catalogue.domain.Book;
import com.whoisacat.edu.book.jpa.catalogue.domain.Genre;
import com.whoisacat.edu.book.jpa.catalogue.service.exception.WHOBookAlreadyExists;
import com.whoisacat.edu.book.jpa.catalogue.service.exception.WHODataAccessException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами должен")
@JdbcTest
@ExtendWith(SpringExtension.class)
@Import(BookServiceSimple.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookServiceSimpleTest{

    private static final Author AUTHOR_ODIN = new Author(1L,"odin",new ArrayList<>());
    private static final Genre GENRE_OGIN = new Genre(1L,"ogin");
    private static final Book BOOK_ODIN = new Book(1L,"bodin",AUTHOR_ODIN,GENRE_OGIN);
    private static final String GENRE_STRING = "genreString";
    private static final String AUTHOR_STRING = "authorString";

    @Autowired
    private BookServiceSimple service;

    @MockBean private BookDaoJdbc dao;
    @MockBean private AuthorService authorService;
    @MockBean private GenreService genreService;

    @DisplayName(value = "Должен вернуть то же что и дао")
    @Test
    void findAll(){
        when(dao.getAll()).thenReturn(Lists.newArrayList(BOOK_ODIN));
        assertThat(service.findAll()).isEqualTo(Lists.newArrayList(BOOK_ODIN));
    }

    @DisplayName(value = "Должен выбросить исключение если книга не добавилась")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void throwExceptionWhenDaoGivesNull(){
        when(authorService.findByNameOrCreate(AUTHOR_STRING))
                .thenReturn(AUTHOR_ODIN);
        when(genreService.findByNameOrCreate(GENRE_STRING)).thenReturn(GENRE_OGIN);
        when(dao.findByNameAndAuthorIdAndGenreId(BOOK_ODIN.getName(),AUTHOR_ODIN.getId(),GENRE_OGIN.getId()))
                .thenReturn(Lists.newArrayList());
        when(dao.insert(any(Book.class))).thenReturn(null);
        assertThrows(WHODataAccessException.class,
                ()->service.addBook(BOOK_ODIN.getName(),AUTHOR_STRING,GENRE_STRING));
    }

    @DisplayName(value = "Должен Выбросить исключение когда книги уже есть")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void returnFoundedBookWhenIsFounded(){
        when(authorService.findByNameOrCreate(AUTHOR_STRING))
                .thenReturn(AUTHOR_ODIN);
        when(genreService.findByNameOrCreate(GENRE_STRING)).thenReturn(GENRE_OGIN);
        when(dao.findByNameAndAuthorIdAndGenreId("name",1,1))
                .thenReturn(Lists.newArrayList(BOOK_ODIN));
        assertThrows(WHOBookAlreadyExists.class,()->service.addBook("name",AUTHOR_STRING,GENRE_STRING));
    }

    @DisplayName(value = "Должен вернуть книгу, если добавил")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void addBook(){
        when(authorService.findByNameOrCreate(AUTHOR_STRING))
                .thenReturn(AUTHOR_ODIN);
        when(genreService.findByNameOrCreate(GENRE_STRING)).thenReturn(GENRE_OGIN);
        when(dao.findByNameAndAuthorIdAndGenreId("name",1,1))
                .thenReturn(Lists.newArrayList());
        when(dao.insert(any(Book.class))).thenReturn(1L);
        when(dao.getById(1L)).thenReturn(BOOK_ODIN);
        Book book = service.addBook(BOOK_ODIN.getName(),AUTHOR_STRING,GENRE_STRING);
        assertThat(book.getName())
                .isEqualTo(BOOK_ODIN.getName());
    }

    @DisplayName(value = "Должен передать что ему сказало дао")
    @Test
    void getBooksCount(){
        when(dao.count()).thenReturn(5432L);
        assertThat(service.getBooksCount()).isEqualTo(5432);
    }

    @DisplayName(value = "Должен передать что ему сказало дао")
    @Test
    void findByAuthorId(){
        when(dao.getByAuthor(707)).thenReturn(Lists.newArrayList(BOOK_ODIN));
        assertThat(service.findByAuthorId(707)).isEqualTo(Lists.newArrayList(BOOK_ODIN));
    }
}
