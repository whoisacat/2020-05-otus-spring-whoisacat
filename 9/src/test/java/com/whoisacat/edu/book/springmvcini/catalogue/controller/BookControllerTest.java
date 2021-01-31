package com.whoisacat.edu.book.springmvcini.catalogue.controller;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Author;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Genre;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.UserSettings;
import com.whoisacat.edu.book.springmvcini.catalogue.repository.AuthorRepository;
import com.whoisacat.edu.book.springmvcini.catalogue.repository.BookRepository;
import com.whoisacat.edu.book.springmvcini.catalogue.repository.CommentRepository;
import com.whoisacat.edu.book.springmvcini.catalogue.repository.GenreRepository;
import com.whoisacat.edu.book.springmvcini.catalogue.service.BookService;
import com.whoisacat.edu.book.springmvcini.catalogue.service.BookServiceMvc;
import com.whoisacat.edu.book.springmvcini.catalogue.service.UserSettingsService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class) //интеграционный
class BookControllerTest {

    private static final String BOOK_TITLE = "BOOK_TITLE";
    private static final String BOOK_TITLE_TWO = "BOOK_TITLE_TWO";
    private static final Author AUTHOR_ODIN = new Author("1L","odin");
    private static final Author AUTHOR_TWO = new Author("1L","two");
    private static final Genre GENRE_OGIN = new Genre("1L","ogin");
    private static final Genre GENRE_TGO = new Genre("1L","tgo");
    private static final Book BOOK_ODIN = new Book("1L",BOOK_TITLE,AUTHOR_ODIN,GENRE_OGIN);
    private static final Book BOOK_TWO = new Book("2L",BOOK_TITLE,AUTHOR_TWO,GENRE_OGIN);
    private static final Book BOOK_THREE = new Book("3L",BOOK_TITLE_TWO,AUTHOR_ODIN,GENRE_TGO);

    @Autowired MockMvc mvc;
    @Autowired ApplicationContext applicationContext;

    @MockBean private BookService bookService;
    @MockBean private UserSettingsService userSettingsService;
    @MockBean private BookRepository bookRepository;
    @MockBean private AuthorRepository authorRepository;
    @MockBean private GenreRepository genreRepository;
    @MockBean private CommentRepository commentRepository;

    @Test
    void mustGetFirstPageList() throws Exception {

        UserSettings userSettings = new UserSettings();
        userSettings.setRowsPerPage(3);
        when(userSettingsService.getUserSettings())
                .thenReturn(userSettings);
        PageImpl<Book> page = new PageImpl<Book>(Lists.newArrayList(BOOK_ODIN, BOOK_TWO, BOOK_THREE));
        when(bookService.findAll(PageRequest.of(0, 3)))
                .thenReturn(page);
        mvc.perform(get("/"))
           .andExpect(status().isOk())
           .andExpect(view().name("list"))
           .andExpect(model().hasNoErrors())
           .andExpect(model().size(1))
           .andExpect(model().attribute("books",page));
    }

    @Test
    void mustGetDeterminedPageList() throws Exception {
        UserSettings userSettings = new UserSettings();
        userSettings.setRowsPerPage(3);
        when(userSettingsService.getUserSettings())
                .thenReturn(userSettings);
        PageImpl<Book> page = new PageImpl<Book>(Lists.newArrayList(BOOK_ODIN, BOOK_TWO, BOOK_THREE));
        when(bookService.findAll(PageRequest.of(2, 3)))
                .thenReturn(page);
        mvc.perform(get("/2"))
           .andExpect(status().isOk())
           .andExpect(view().name("list"))
           .andExpect(model().hasNoErrors())
           .andExpect(model().size(1))
           .andExpect(model().attribute("books",page));
    }

    @Test
    void mustRedirectOnFirstPageList() throws Exception {

        UserSettings userSettings = new UserSettings();
        userSettings.setRowsPerPage(3);
        when(userSettingsService.getUserSettings())
                .thenReturn(userSettings);
        PageImpl<Book> page = new PageImpl<Book>(Lists.newArrayList(BOOK_ODIN, BOOK_TWO, BOOK_THREE));
        when(bookService.findAll(PageRequest.of(0, 3)))
                .thenReturn(page);
        mvc.perform(get("/0"))
           .andExpect(status().isFound())
           .andExpect(view().name("redirect:/"))
           .andExpect(model().hasNoErrors())
           .andExpect(redirectedUrl("/"));
    }

    @Test
    void mustGetErr500View() throws Exception {
        PageImpl<Book> page = new PageImpl<Book>(Lists.newArrayList(BOOK_ODIN, BOOK_TWO, BOOK_THREE));
        when(bookService.findAll(PageRequest.of(0, 3)))
                .thenReturn(page);
        mvc.perform(get("/0"))
           .andExpect(status().isBadRequest())
           .andExpect(view().name("err400"))
           .andExpect(model().hasNoErrors());
    }
}
