package com.whoisacat.edu.book.jdbc.catalogue.service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.jdbc.catalogue.dao.AuthorDao;
import com.whoisacat.edu.book.jdbc.catalogue.domain.Author;
import com.whoisacat.edu.book.jdbc.catalogue.domain.Book;
import com.whoisacat.edu.book.jdbc.catalogue.domain.Genre;
import com.whoisacat.edu.book.jdbc.catalogue.service.exception.WHORequestClientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с авторами должен")
@JdbcTest
@ExtendWith(SpringExtension.class)
@Import(AuthorServiceSimple.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class AuthorServiceSimpleTest{

    private static final Genre GENRE_1 = new Genre(1L,"1");
    private static final Author AUTHOR_1 = new Author(1L,"1",new ArrayList<>());
    private static final Book BOOK_1 = new Book(1L,"1",AUTHOR_1,GENRE_1);
    private static final Author AUTHOR_1_WITH_BOOK = new Author(1L,"1",Lists.newArrayList(BOOK_1));
    private static final Author AUTHOR_2 = new Author(2L,"2",new ArrayList<>());

    @Autowired
    private AuthorServiceSimple service;

    @MockBean
    private AuthorDao dao;

    @Test
    void getAllAuthorsString(){
        when(dao.getAll())
                .thenReturn(Lists.newArrayList(AUTHOR_1_WITH_BOOK,AUTHOR_2));
        assertThat(service.getAllAuthorsString()).isEqualTo("1 Книги: 1; \n2 Книги: \n");
    }

    @Test
    void foundByNameOneAuthor(){
        when(dao.getByName("name")).thenReturn(Lists.newArrayList(AUTHOR_2));
        assertThat(service.findByNameOrCreate("name")).isEqualTo(AUTHOR_2);
    }

    @Test
    void findByNameOrCreate(){
        when(dao.getByName("name")).thenReturn(Lists.newArrayList(AUTHOR_2,AUTHOR_1_WITH_BOOK));
        assertThrows(WHORequestClientException.class,() -> service.findByNameOrCreate("name"));
    }

    @Test
    void getAuthorsCount(){
        when(dao.count()).thenReturn(707L);
        assertThat(service.getAuthorsCount()).isEqualTo(707);
    }

    @Test
    void foundNoOneAuthorByName(){
        when(dao.getByName("name")).thenReturn(new ArrayList<>());
        assertThat(service.findByName("name")).isEqualTo(AuthorServiceSimple.NOT_FOUND);
    }

    @Test
    void foundCoupleOfAuthorsByName(){
        when(dao.getByName("name")).thenReturn(Lists.newArrayList(AUTHOR_1_WITH_BOOK,AUTHOR_2));
        assertThat(service.findByName("name")).isEqualTo("1; 2; ");
    }
}
