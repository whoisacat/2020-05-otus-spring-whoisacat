package com.whoisacat.edu.book.springmvcini.catalogue.repository;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Author;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;

@DisplayName("Dao для работы с комментариями должно")
@DataMongoTest
@ComponentScan({"com.whoisacat.edu.book.springmvcini.catalogue.mongock",
        "com.whoisacat.edu.book.springmvcini.catalogue.repositories"})
class CommentRepositoryTest {

    public static final String CLEAN_C = "Чистый к";
    public static final String ROBERT_MA = "Роберт Ма";
    public static final String COMMENT_451 = "фигня";
    @Autowired
    private CommentRepository repository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Правильно насчитать семь комментариев")
    @Test
    void count(){
        assertThat(repository.count()).isEqualTo(7);
    }

    @DisplayName("Найти все комментарии по книге")
    @Test
    void getAllByBook(){
        Book book = bookRepository.getAllByTitleContains(CLEAN_C).get(0);
        List<Comment> comments = repository.getAllByBook(book,PageRequest.of(0,10));
        assertThat(comments.size()).isEqualTo(3);
        comments.forEach(c->{
            assertThat(c.getBook().getId()).isEqualTo(book.getId());
            assertThat(c.getBook().getTitle()).isEqualTo(book.getTitle());
            assertThat(c.getBook().getAuthor().getTitle()).isEqualTo(book.getAuthor().getTitle());
            assertThat(c.getBook().getGenre().getTitle()).isEqualTo(book.getGenre().getTitle());
        });
    }

    @DisplayName("Найти все комментарии по автору")
    @Test
    void getAllByBookAuthor(){
        Author author = authorRepository.getByTitleContains(ROBERT_MA).get(0);
        List<Comment> comments = repository.getAllByBookAuthor(author,PageRequest.of(0,10));
        assertThat(comments.size()).isEqualTo(4);
        comments.forEach(c->{
            assertThat(c.getBook().getAuthor().getId()).isEqualTo(author.getId());
            assertThat(c.getBook().getAuthor().getTitle()).isEqualTo(author.getTitle());
        });
    }

    @DisplayName("Найти три комментария по автору")
    @Test
    void getOneLatestCommentsByBookAuthor(){
        Author author = authorRepository.getByTitleContains(ROBERT_MA).get(0);
        List<Comment> comments = repository.getAllByBookAuthor(author,PageRequest.of(1,3));
        assertThat(comments.size()).isEqualTo(1);
        comments.forEach(c->{
            assertThat(c.getBook().getAuthor().getId()).isEqualTo(author.getId());
            assertThat(c.getBook().getAuthor().getTitle()).isEqualTo(author.getTitle());
        });
    }

    @DisplayName("Найти три комментария по автору")
    @Test
    void getThreeFirstCommentsByBookAuthor(){
        Author author = authorRepository.getByTitleContains(ROBERT_MA).get(0);
        List<Comment> comments = repository.getAllByBookAuthor(author,PageRequest.of(0,3));
        assertThat(comments.size()).isEqualTo(3);
        comments.forEach(c->{
            assertThat(c.getBook().getAuthor().getId()).isEqualTo(author.getId());
            assertThat(c.getBook().getAuthor().getTitle()).isEqualTo(author.getTitle());
        });
    }

    @DisplayName("Найти все комментарии с фразой в титле")
    @Test
    void getAllByTitleContains(){
        assertThat(repository.getAllByTitleContains("Лин").size()).isEqualTo(1);
    }

    @DisplayName("Найти все комментарии с фразой в тексте")
    @Test
    void getAllByTextContaining(){
        assertThat(repository.getAllByTextContainingIgnoreCase("Качест").size()).isEqualTo(3);
    }

    @DisplayName("Удалить один комментарий по титлу")
    @DirtiesContext(methodMode = AFTER_METHOD)
    @Test
    void deleteByTitle(){
        assertThat(repository.count()).isEqualTo(7);
        repository.deleteByTitle(COMMENT_451);
        assertThat(repository.count()).isEqualTo(6);
    }

    @DisplayName("Удалять комментарии по автору")
    @DirtiesContext(methodMode = AFTER_METHOD)
    @Test
    void deleteCommentsByBook_AuthorWorks(){
        Author author = authorRepository.getByTitleContains(ROBERT_MA).get(0);
        assertThat(repository.count()).isEqualTo(7);
        repository.deleteCommentsByBook_Author(author);
        assertThat(repository.count()).isEqualTo(3);
    }

    @DisplayName("Удаление возвращает количество удаленных комментариев")
    @DirtiesContext(methodMode = AFTER_METHOD)
    @Test
    void deleteCommentsByBook_AuthorReturnsCountOfDeletedComments(){
        Author author = authorRepository.getByTitleContains(ROBERT_MA).get(0);
        assertThat(repository.deleteCommentsByBook_Author(author)).isEqualTo(4);
    }
}
