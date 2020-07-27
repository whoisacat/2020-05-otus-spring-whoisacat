package com.whoisacat.edu.book.mongodb.catalogue.service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Author;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Book;
import com.whoisacat.edu.book.mongodb.catalogue.domain.Genre;
import com.whoisacat.edu.book.mongodb.catalogue.service.exception.WHORequestClientException;
import com.whoisacat.edu.book.mongodb.catalogue.repository.AuthorMongoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с авторами должен")
@Import(AuthorServiceSimple.class)
@DataMongoTest
class AuthorServiceSimpleTest{

    private static final Genre GENRE_1 = new Genre("1","1");
    private static final Author AUTHOR_1 = new Author("1","1");
    private static final Book BOOK_1 = new Book("1","1",AUTHOR_1,GENRE_1);
    private static final Author AUTHOR_1_WITH_BOOK = new Author("1","1");
    private static final Author AUTHOR_2 = new Author("2","2");

    @Autowired
    private AuthorServiceSimple service;

    @MockBean
    private AuthorMongoRepository repository;
    @MockBean
    private BookService bookService;

    @Test
    void getAllAuthorsString(){
        when(repository.getAllBy())
                .thenReturn(Lists.newArrayList(AUTHOR_1_WITH_BOOK,AUTHOR_2));
        when(bookService.findByAuthorTitle("1")).thenReturn(Collections.singletonList(BOOK_1));
        assertThat(service.getAllAuthorsString()).isEqualTo("1 Книги: 1; \n2 Книги: \n");
    }

    @Test
    void foundByNameOneAuthor(){
        when(repository.getByTitleContains("name")).thenReturn(Lists.newArrayList(AUTHOR_2));
        assertThat(service.findByNameOrCreate("name")).isEqualTo(AUTHOR_2);
    }

    @Test
    void findByNameOrCreate(){
        when(repository.getByTitleContains("name")).thenReturn(Lists.newArrayList(AUTHOR_2,AUTHOR_1_WITH_BOOK));
        assertThrows(WHORequestClientException.class,() -> service.findByNameOrCreate("name"));
    }

    @Test
    void getAuthorsCount(){
        when(repository.count()).thenReturn(707L);
        assertThat(service.getAuthorsCount()).isEqualTo(707);
    }

    @Test
    void foundNoOneAuthorByName(){
        when(repository.getByTitleContains("name")).thenReturn(new ArrayList<>());
        assertThat(service.findAuthorsByTitle("name")).isEqualTo(AuthorServiceSimple.NOT_FOUND);
    }

    @Test
    void foundCoupleOfAuthorsByName(){
        when(repository.getByTitleContains("name")).thenReturn(Lists.newArrayList(AUTHOR_1_WITH_BOOK,AUTHOR_2));
        assertThat(service.findAuthorsByTitle("name")).isEqualTo("1; 2; ");
    }
}
