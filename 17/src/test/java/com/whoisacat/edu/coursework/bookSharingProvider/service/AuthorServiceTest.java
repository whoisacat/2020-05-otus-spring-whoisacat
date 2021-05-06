package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.AuthorRepository;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Author;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Genre;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHORequestClientException;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с авторами должен")
@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(AuthorServiceImpl.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureEmbeddedDatabase
class AuthorServiceTest {

    private static final Genre GENRE_1 = new Genre(1L,"1");
    private static final Author AUTHOR_1 = new Author(1L,"1");
    private static final Book BOOK_1 = new Book(1L,"1",AUTHOR_1,GENRE_1);
    private static final Author AUTHOR_1_WITH_BOOK = new Author(1L,"1");
    private static final Author AUTHOR_2 = new Author(2L,"2");

    @Autowired
    private AuthorServiceImpl service;

    @MockBean
    private AuthorRepository repository;
    @MockBean
    private BookService bookService;

    @Test
    void getAllAuthorsString(){
        when(repository.getAllBy())
                .thenReturn(Lists.newArrayList(AUTHOR_1_WITH_BOOK,AUTHOR_2));
        when(bookService.findByAuthorId(1L)).thenReturn(Collections.singletonList(BOOK_1));
        assertThat(service.getAllAuthorsString()).isEqualTo("1 Книги: 1; \n2 Книги: \n");
    }

    @Test
    void foundByNameOneAuthor(){
        when(repository.getByTitle("name")).thenReturn(Lists.newArrayList(AUTHOR_2));
        assertThat(service.findByNameOrCreate("name")).isEqualTo(AUTHOR_2);
    }

    @Test
    void findByNameOrCreate(){
        when(repository.getByTitle("name")).thenReturn(Lists.newArrayList(AUTHOR_2,AUTHOR_1_WITH_BOOK));
        assertThrows(WHORequestClientException.class,() -> service.findByNameOrCreate("name"));
    }

    @Test
    void getAuthorsCount(){
        when(repository.count()).thenReturn(707L);
        assertThat(service.getAuthorsCount()).isEqualTo(707);
    }

    @Test
    void foundNoOneAuthorByName(){
        when(repository.getByTitle("name")).thenReturn(new ArrayList<>());
        assertThat(service.findByName("name")).isEqualTo(AuthorServiceImpl.NOT_FOUND);
    }

    @Test
    void foundCoupleOfAuthorsByName(){
        when(repository.getByTitle("name")).thenReturn(Lists.newArrayList(AUTHOR_1_WITH_BOOK,AUTHOR_2));
        assertThat(service.findByName("name")).isEqualTo("1; 2; ");
    }
}
