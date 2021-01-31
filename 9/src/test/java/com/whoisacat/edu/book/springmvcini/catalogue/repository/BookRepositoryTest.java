package com.whoisacat.edu.book.springmvcini.catalogue.repository;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Author;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами должно")
@DataMongoTest
@ComponentScan({"com.whoisacat.edu.book.springmvcini.catalogue.mongock",
        "com.whoisacat.edu.book.springmvcini.catalogue.repositories"})
class BookRepositoryTest {

    public static final String CLEAN_ARCH_TITLE = "Чистая архитектура";
    public static final String UNCLE_BOB_TITLE = "Роберт Мартин";
    public static final String PROGRAMMING_TITLE = "Программирование";
    public static final String CLEAN_CODER_TITLE = "Идевльный программист";
    public static final String THE_ART_OF_WAR = "Исскуство войны";
    @Autowired BookRepository repository;
    private static final Author ROBERT_MARTIN = new Author("3","Роберт Мартин");;
    private static final Genre PROGRAMMING = new Genre("1","Программирование");
    private static final Book CLEAN_CODE = new Book("2","Чистый код",ROBERT_MARTIN,PROGRAMMING);;

    @DisplayName("Правильно насчитать шесть книг")
    @Test
    void count(){
        assertThat(6).isEqualTo(repository.count());
    }

    @DisplayName("Вставить книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insert(){
        assertThat(repository.count()).isEqualTo(6);
        repository.save(new Book(null,CLEAN_ARCH_TITLE,
            new Author("3",""),
            new Genre("1","")));
        assertThat(repository.count()).isEqualTo(7);
    }

    @DisplayName("Вставить книгу с правильным автором")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertWithCorrectAuthorName(){
        Book book = repository.save(new Book(CLEAN_ARCH_TITLE,
                new Author(UNCLE_BOB_TITLE),
                new Genre(PROGRAMMING_TITLE)));
        assertThat(CLEAN_ARCH_TITLE).isEqualTo(book.getTitle());
        assertThat(book.getAuthor().getTitle()).isEqualTo(UNCLE_BOB_TITLE);
    }

    @DisplayName("Вставить книгу с правильным названием")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertWithCorrectName(){
        Book book = repository.save(new Book(CLEAN_ARCH_TITLE,
                new Author("3",""),
                new Genre("1","")));
        assertThat(book.getTitle()).isEqualTo(CLEAN_ARCH_TITLE);
    }

    @DisplayName("Найти книгу по названию")
    @Test
    void getById(){
        Book actual = repository.getBooksByTitleLike(CLEAN_CODE.getTitle()).get(0);
        assertThat(CLEAN_CODE.getTitle()).isEqualTo(actual.getTitle());
        assertThat(CLEAN_CODE.getAuthor().getTitle()).isEqualTo(actual.getAuthor().getTitle());
        assertThat(CLEAN_CODE.getGenre().getTitle()).isEqualTo(actual.getGenre().getTitle());
    }

    @DisplayName("Найти книгу по названию")
    @Test
    void getByName(){
        List<Book> actualList = repository.getAllByTitleContains("Чистый");
        assertThat(1).isEqualTo(actualList.size());
        Book actual = actualList.get(0);
        assertThat(CLEAN_CODE.getTitle()).isEqualTo(actual.getTitle());
        assertThat(CLEAN_CODE.getAuthor().getTitle()).isEqualTo(actual.getAuthor().getTitle());
        assertThat(CLEAN_CODE.getGenre().getTitle()).isEqualTo(actual.getGenre().getTitle());
    }

    @DisplayName("Найти книгу по автору")
    @Test
    void getByAuthor(){

        List<Book> actual = repository.getByAuthor_Title(UNCLE_BOB_TITLE);

        Author author = new Author(UNCLE_BOB_TITLE);
        Genre genre = new Genre(PROGRAMMING_TITLE);
        Book expected2 = new Book("3",CLEAN_CODER_TITLE,author,genre);
        List<Book> expected = Lists.newArrayList(CLEAN_CODE,expected2);
        expected.sort(Comparator.comparing(Book::getId));
        actual.sort(Comparator.comparing(Book::getId));
        assertThat(expected.size()).isEqualTo(actual.size());
        assertThat(expected.get(0).getTitle() == actual.get(0).getTitle());
        assertThat(expected.get(1).getTitle() == actual.get(1).getTitle());
        assertThat(expected.get(0).getAuthor().getTitle() == actual.get(0).getAuthor().getTitle());
        assertThat(expected.get(1).getAuthor().getTitle() == actual.get(1).getAuthor().getTitle());
        assertThat(expected.get(0).getGenre().getTitle() == actual.get(0).getGenre().getTitle());
        assertThat(expected.get(1).getGenre().getTitle() == actual.get(1).getGenre().getTitle());
    }

    @DisplayName(value = "Вернуть список из всех книг")
    @Test
    void getAll(){
        assertThat(repository.getAllBy().size()).isEqualTo(6);
    }

    @DisplayName(value = "Удалить книгу по названию")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteById(){
        long count = repository.count();
        String id = repository.getBooksByTitleLike(CLEAN_CODE.getTitle()).get(0).getId();
        assertThat(repository.getById(id)).isNotNull();
        assertThat(repository.deleteByTitle(CLEAN_CODE.getTitle())).isEqualTo(1);
        assertThat(count - 1).isEqualTo(repository.count());
        assertThat(repository.getById(id)).isNull();
    }

    @DisplayName(value = "Удалить книгу по имени")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteByName(){
        long count = repository.count();
        String id = repository.getBooksByTitleLike(THE_ART_OF_WAR).get(0).getId();
        assertThat(repository.getById(id)).isNotNull();
        assertThat(1).isEqualTo(repository.deleteByTitle(THE_ART_OF_WAR));
        assertThat(count - 1).isEqualTo(repository.count());
        assertThat(repository.getById(id)).isNull();
    }

    @DisplayName(value = "Найти книгу по всем названиям")
    @Test
    void findByNameAndAuthorIdAndGenreId(){
        List<Book> actual = repository.findByTitleContainsAndAuthorTitleLikeAndGenreTitleLike("Чистый ко",UNCLE_BOB_TITLE,PROGRAMMING_TITLE);
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getTitle()).isEqualTo("Чистый код");
    }
}
