package com.whoisacat.edu.book.springmvcini.catalogue.service;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Author;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Comment;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Genre;
import com.whoisacat.edu.book.springmvcini.catalogue.dto.CommentDTO;
import com.whoisacat.edu.book.springmvcini.catalogue.repository.CommentRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с комментариями должен")
@ExtendWith(SpringExtension.class)
@Import(CommentServiceMvc.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CommentServiceMvcTest {

    private static final String BOOK_TITLE = "BOOK_TITLE";
    private static final Author AUTHOR_ODIN = new Author("1L","odin");
    private static final Author AUTHOR_TWO = new Author("1L","two");
    private static final Genre GENRE_OGIN = new Genre("1L","ogin");
    private static final Book BOOK_ODIN = new Book("1L",BOOK_TITLE,AUTHOR_ODIN,GENRE_OGIN);
    private static final Book BOOK_TWO = new Book("2L",BOOK_TITLE,AUTHOR_TWO,GENRE_OGIN);
    private static final String GENRE_STRING = "genreString";
    private static final String AUTHOR_STRING = "authorString";
    private static final Comment COMMENT_1 = new Comment("01","first","first_text",BOOK_ODIN);
    private static final Comment COMMENT_2 = new Comment("02","second","second_text",BOOK_ODIN);
    private static final Comment COMMENT_3 = new Comment("03","third","third_text",BOOK_TWO);
    public static final ArrayList<String> EXPECTED_DTO_LIST_STRING = Lists.newArrayList(
            "CommentDTO{id='" + COMMENT_1.getId() + "', " +
                    "title='" + COMMENT_1.getTitle() + "', " +
                    "text='" + COMMENT_1.getText() + "'}",
            "CommentDTO{id='" + COMMENT_2.getId() + "', " +
                    "title='" + COMMENT_2.getTitle() + "', " +
                    "text='" + COMMENT_2.getText() + "'}",
            "CommentDTO{id='" + COMMENT_3.getId() + "', " +
                    "title='" + COMMENT_3.getTitle() + "', " +
                    "text='" + COMMENT_3.getText() + "'}"
    );
    private static final int PAGE = 1;
    private static final Integer PAGE_SIZE = 3;

    @Autowired
    private CommentServiceMvc service;

    @MockBean private CommentRepository repository;
    @MockBean private AuthorService authorService;
    @MockBean private BookService bookService;


    @DisplayName(value = "Должен вернуть столько же книг, сколько репозиторий, постранично")
    @Test
    void findThreeBooksCommentsByBookTitlePageable() {
        List<Book> books = Lists.newArrayList(BOOK_ODIN, BOOK_TWO);
        when(bookService.findBooksWithSubstringInTitle(BOOK_TITLE))
                .thenReturn(books);
        when(repository.getAllByBookIn(books, PageRequest.of(1,3)))
                .thenReturn(Lists.newArrayList(COMMENT_1, COMMENT_1, COMMENT_1));
        List<CommentDTO> dtos = service.findBookCommentsByBookTitle(BOOK_TITLE, PAGE, PAGE_SIZE);
        assertThat(dtos.size()).isEqualTo(3);
    }

    @DisplayName(value = "Должен вернуть список dto из трех книг, возращенных репозиторием, постранично")
    @Test
    void findBookCommentsByBookTitlePageable() {
        List<Book> books = Lists.newArrayList(BOOK_ODIN, BOOK_TWO);
        when(bookService.findBooksWithSubstringInTitle(BOOK_TITLE))
                .thenReturn(books);
        when(repository.getAllByBookIn(books, PageRequest.of(1,3)))
                .thenReturn(Lists.newArrayList(COMMENT_1, COMMENT_2, COMMENT_3));
        List<CommentDTO> dtos = service.findBookCommentsByBookTitle(BOOK_TITLE, PAGE, PAGE_SIZE);
        assertThat(dtos.stream().map(Object::toString))
                .isEqualTo(EXPECTED_DTO_LIST_STRING);
    }

    @DisplayName(value = "Должен вернуть столько же книг, сколько репозиторий")
    @Test
    void findThreeBooksCommentsByBookTitle() {
        List<Book> books = Lists.newArrayList(BOOK_ODIN, BOOK_TWO);
        when(bookService.findBooksWithSubstringInTitle(BOOK_TITLE))
                .thenReturn(books);
        when(repository.getAllByBookIn(books))
                .thenReturn(Lists.newArrayList(COMMENT_1, COMMENT_1, COMMENT_1));
        List<CommentDTO> dtos = service.findBookCommentsByBookTitle(BOOK_TITLE);
        assertThat(dtos.size()).isEqualTo(3);
    }

    @DisplayName(value = "Должен вернуть список dto из трех книг, возращенных репозиторием")
    @Test
    void findBooksCommentsByBookTitle() {
        List<Book> books = Lists.newArrayList(BOOK_ODIN, BOOK_TWO);
        when(bookService.findBooksWithSubstringInTitle(BOOK_TITLE))
                .thenReturn(books);
        when(repository.getAllByBookIn(books))
                .thenReturn(Lists.newArrayList(COMMENT_1, COMMENT_2, COMMENT_3));
        List<CommentDTO> dtos = service.findBookCommentsByBookTitle(BOOK_TITLE);
        assertThat(dtos.stream().map(Object::toString))
                .isEqualTo(EXPECTED_DTO_LIST_STRING);
    }

    @DisplayName(value = "Должен вернуть пустой список dto, если репозиторий не вернул книг")
    @Test
    void findNoBooksCommentsByBookTitle() {
        when(repository.getAllByBookIn(anyList()))
                .thenReturn(Lists.newArrayList(new ArrayList<>()));
        List<CommentDTO> dtos = service.findBookCommentsByBookTitle(BOOK_TITLE);
        assertThat(dtos.stream().map(Object::toString))
                .isEqualTo(new ArrayList<>());
    }

    @DisplayName(value = "Должен вернуть три книг. Только по авторам. Постранично")
    @Test
    void findThreeBooksCommentsByBookAuthorTitlePageable() {
        List<Author> authors = Lists.newArrayList(AUTHOR_ODIN, AUTHOR_TWO);
        when(authorService.findAuthorsWithSubstringInTitle(AUTHOR_STRING))
                .thenReturn(authors);
        when(repository.getAllByBookAuthorIn(authors, PageRequest.of(1,3)))
                .thenReturn(Lists.newArrayList(COMMENT_1, COMMENT_2, COMMENT_3));
        assertThat(service
                .findBookCommentsByBookAuthorTitle(AUTHOR_STRING, PAGE, PAGE_SIZE).size())
                .isEqualTo(3);
    }

    @DisplayName(value = "Должен вернуть столько же книг, сколько репозиторий. Только по авторам. Постранично.")
    @Test
    void findBookCommentsByBookAuthorTitlePageable() {
        List<Author> authors = Lists.newArrayList(AUTHOR_ODIN, AUTHOR_TWO);
        when(authorService.findAuthorsWithSubstringInTitle(AUTHOR_STRING))
                .thenReturn(authors);
        when(repository.getAllByBookAuthorIn(authors, PageRequest.of(1,3)))
                .thenReturn(Lists.newArrayList(COMMENT_1, COMMENT_2, COMMENT_3));
        assertThat(service
                .findBookCommentsByBookAuthorTitle(AUTHOR_STRING, PAGE, PAGE_SIZE).stream().map(Objects::toString))
                .isEqualTo(EXPECTED_DTO_LIST_STRING);
    }

    @DisplayName(value = "Должен вернуть три книг. Только по авторам.")
    @Test
    void findTreeBooksCommentsByBookAuthorTitle() {
        List<Author> authors = Lists.newArrayList(AUTHOR_ODIN, AUTHOR_TWO);
        when(authorService.findAuthorsWithSubstringInTitle(AUTHOR_STRING))
                .thenReturn(authors);
        when(repository.getAllByBookAuthorIn(authors))
                .thenReturn(Lists.newArrayList(COMMENT_1, COMMENT_2, COMMENT_3));
        assertThat(service
                .findBookCommentsByBookAuthorTitle(AUTHOR_STRING).size())
                .isEqualTo(3);
    }

    @DisplayName(value = "Должен вернуть столько же книг, сколько репозиторий. Только по авторам.")
    @Test
    void findBookCommentsByBookAuthorTitle() {
        List<Author> authors = Lists.newArrayList(AUTHOR_ODIN, AUTHOR_TWO);
        when(authorService.findAuthorsWithSubstringInTitle(AUTHOR_STRING))
                .thenReturn(authors);
        when(repository.getAllByBookAuthorIn(authors))
                .thenReturn(Lists.newArrayList(COMMENT_1, COMMENT_2, COMMENT_3));
        assertThat(service
                .findBookCommentsByBookAuthorTitle(AUTHOR_STRING).stream().map(Objects::toString))
                .isEqualTo(EXPECTED_DTO_LIST_STRING);
    }

    @DisplayName(value = "Должен вернуть DTO сущности, которую вернул репозиторий")
    @Test
    void addComment() {
        when(bookService.findByOwnAndAuthorAndGenreTitles(BOOK_TITLE,AUTHOR_STRING,GENRE_STRING))
                .thenReturn(BOOK_ODIN);
        when(repository.save(isA(Comment.class)))
                .thenReturn(new Comment("raz", "raz", "escho_raz", BOOK_ODIN));
        assertThat(service.addComment("raz", "escho_raz", BOOK_TITLE, AUTHOR_STRING, GENRE_STRING).toString())
                .isEqualTo(new CommentDTO("raz", "raz", "escho_raz").toString());
    }
}
