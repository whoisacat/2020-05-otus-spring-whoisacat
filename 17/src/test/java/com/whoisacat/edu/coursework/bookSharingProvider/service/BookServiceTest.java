package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.BookRepository;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Author;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Genre;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHOBookAlreadyExists;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHODataAccessException;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами должен")
@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(BookServiceMvc.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureEmbeddedDatabase
class BookServiceTest {

    private static final Author AUTHOR_ODIN = new Author(1L,"odin");
    private static final Genre GENRE_OGIN = new Genre(1L,"ogin");
    private static final Book BOOK_ODIN = new Book(1L,"bodin",AUTHOR_ODIN,GENRE_OGIN);
    private static final String GENRE_STRING = "genreString";
    private static final String AUTHOR_STRING = "authorString";

    @Autowired
    private BookServiceMvc service;

    @MockBean private BookRepository repository;
    @MockBean private AuthorService authorService;
    @MockBean private GenreService genreService;

    @DisplayName(value = "Должен вернуть то же что и дао")
    @Test
    void findAll(){
        PageRequest pr = PageRequest.of(0,100);
        when(repository.getAllBy(pr)).thenReturn(Lists.newArrayList(BOOK_ODIN));
        Page<Book> page = new PageImpl<>(Lists.newArrayList(BOOK_ODIN));
        assertThat(service.findAll(pr).toList()).isEqualTo(page.toList());
    }

    @DisplayName(value = "Должен выбросить исключение если книга не добавилась")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void throwExceptionWhenDaoGivesNull(){
        when(authorService.findByNameOrCreate(AUTHOR_STRING))
                .thenReturn(AUTHOR_ODIN);
        when(genreService.findByNameOrCreate(GENRE_STRING)).thenReturn(GENRE_OGIN);
        when(repository.findByTitleContainsAndAuthorIdAndGenreId(BOOK_ODIN.getTitle(),AUTHOR_ODIN.getId(),GENRE_OGIN.getId()))
                .thenReturn(Lists.newArrayList());
        when(repository.save(any(Book.class))).thenReturn(null);
        assertThrows(WHODataAccessException.class,
                ()->service.addBook(BOOK_ODIN.getTitle(),AUTHOR_STRING,GENRE_STRING));
    }

    @DisplayName(value = "Должен Выбросить исключение когда книги уже есть")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void returnFoundedBookWhenIsFounded(){
        when(authorService.findByNameOrCreate(AUTHOR_STRING))
                .thenReturn(AUTHOR_ODIN);
        when(genreService.findByNameOrCreate(GENRE_STRING)).thenReturn(GENRE_OGIN);
        when(repository.findByTitleContainsAndAuthorIdAndGenreId("name",1,1))
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
        when(repository.findByTitleContainsAndAuthorIdAndGenreId("name",1,1))
                .thenReturn(Lists.newArrayList());
        when(repository.save(any(Book.class))).thenReturn(BOOK_ODIN);
        when(repository.getById(1L)).thenReturn(BOOK_ODIN);
        Book book = service.addBook(BOOK_ODIN.getTitle(),AUTHOR_STRING,GENRE_STRING);
        assertThat(book.getTitle())
                .isEqualTo(BOOK_ODIN.getTitle());
    }

    @DisplayName(value = "Должен передать что ему сказало дао")
    @Test
    void getBooksCount(){
        when(repository.count()).thenReturn(5432L);
        assertThat(service.getBooksCount()).isEqualTo(5432);
    }

    @DisplayName(value = "Должен передать что ему сказало дао")
    @Test
    void findByAuthorId(){
        when(repository.getByAuthorId(707)).thenReturn(Lists.newArrayList(BOOK_ODIN));
        assertThat(service.findByAuthorId(707)).isEqualTo(Lists.newArrayList(BOOK_ODIN));
    }
}
