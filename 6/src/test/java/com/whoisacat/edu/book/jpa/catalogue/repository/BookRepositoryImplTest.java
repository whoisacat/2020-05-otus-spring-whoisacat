package com.whoisacat.edu.book.jpa.catalogue.repository;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.jpa.catalogue.domain.Author;
import com.whoisacat.edu.book.jpa.catalogue.domain.Book;
import com.whoisacat.edu.book.jpa.catalogue.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий для работы с книгами должен")
@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(BookRepositoryImpl.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookRepositoryImplTest{

    public static final int INITIAL_BOOKS_COUNT = 6;
    public static final long UNCLE_BOBS_AUTHOR_ID = 3L;
    public static final String CLEAN_ARCH_TITLE = "Чистая архитектура";
    public static final long PROGRAMMING_ID = 1L;
    public static final long INITIAL_BOOKS_COUNT_PLUS_ONE = 7L;
    public static final String UNCLE_BOB_TITLE = "Роберт Мартин";
    public static final String PROGRAMMING_TITLE = "Программирование";
    public static final int CLEAN_CODE_ID = 2;
    public static final String CLEAN_CODE_TITLE_WITHOUT_SUBSTRING_CODE = "Чистый";
    public static final int COUNT_OF_BOOKS_WITH_CLEAN_IN_TITLE = 1;
    public static final int FIRST_INDEX = 0;
    public static final String THE_CLEAN_CODER_TITLE = "Идевльный программист";
    public static final long THE_CLEAN_CODER_ID = 3L;
    public static final int ACTUAL_COUNT_OF_DELETED_ROWS_WITH_DELETING_BY_ID = 1;
    public static final int SUN_TSZY_BOOK_ID = 1;
    public static final String SUN_TSZY_BOOK_TITLE = "Исскуство войны";
    public static final String CLEAN_CO_TITLE = "Чистый ко";
    public static final String CLEAN_CODE_TITLE = "Чистый код";
    @Autowired BookRepositoryImpl repository;
    private static final Author ROBERT_MARTIN = new Author(UNCLE_BOBS_AUTHOR_ID,"Роберт Мартин");;
    private static final Genre PROGRAMMING = new Genre(1L,"Программирование");
    private static final Book CLEAN_CODE = new Book(2L,"Чистый код",ROBERT_MARTIN,PROGRAMMING);;

    @DisplayName("Правильно насчитать шесть книг")
    @Test
    void count(){
        assertThat(INITIAL_BOOKS_COUNT).isEqualTo(repository.count());
    }

    @Transactional
    @DisplayName("Вставить книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insert(){
        assertThat(repository.save(new Book(null,CLEAN_ARCH_TITLE,
                new Author(UNCLE_BOBS_AUTHOR_ID,""),
                new Genre(PROGRAMMING_ID,""))).getId()).isEqualTo(repository.count());
    }

    @Transactional
    @DisplayName("Вставить книгу с правильным автором")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertWithCorrectAuthorName(){
        assertThat(INITIAL_BOOKS_COUNT_PLUS_ONE)
                .isEqualTo(repository.save(new Book(null,CLEAN_ARCH_TITLE,
                new Author(UNCLE_BOBS_AUTHOR_ID,UNCLE_BOB_TITLE),
                new Genre(PROGRAMMING_ID,PROGRAMMING_TITLE))).getId());
        assertThat(repository.getById(repository.count()).getAuthor().getTitle()).isEqualTo(UNCLE_BOB_TITLE);
    }

    @Transactional
    @DisplayName("Вставить книгу с правильным названием")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void insertWithCorrectName(){
        Book book = repository.save(new Book(null,CLEAN_ARCH_TITLE,
                new Author(UNCLE_BOBS_AUTHOR_ID,""),
                new Genre(PROGRAMMING_ID,"")));
        assertThat(INITIAL_BOOKS_COUNT_PLUS_ONE).isEqualTo(book.getId());
        assertThat(book.getTitle()).isEqualTo(CLEAN_ARCH_TITLE);
    }

    @Transactional
    @DisplayName("Найти книгу по идентефикатору")
    @Test
    void getById(){
        Book actual = repository.getById(CLEAN_CODE_ID);
        assertThat(CLEAN_CODE.getId()).isEqualTo(actual.getId());
        assertThat(CLEAN_CODE.getTitle()).isEqualTo(actual.getTitle());
        assertThat(CLEAN_CODE.getAuthor().getId()).isEqualTo(actual.getAuthor().getId());
        assertThat(CLEAN_CODE.getAuthor().getTitle()).isEqualTo(actual.getAuthor().getTitle());
        assertThat(CLEAN_CODE.getGenre().getId()).isEqualTo(actual.getGenre().getId());
        assertThat(CLEAN_CODE.getGenre().getTitle()).isEqualTo(actual.getGenre().getTitle());
    }

    @Transactional
    @DisplayName("Найти книгу по названию")
    @Test
    void getByName(){
        List<Book> actualList = repository.getByName(CLEAN_CODE_TITLE_WITHOUT_SUBSTRING_CODE);
        assertThat(COUNT_OF_BOOKS_WITH_CLEAN_IN_TITLE).isEqualTo(actualList.size());
        Book actual = actualList.get(FIRST_INDEX);
        assertThat(CLEAN_CODE.getId()).isEqualTo(actual.getId());
        assertThat(CLEAN_CODE.getTitle()).isEqualTo(actual.getTitle());
        assertThat(CLEAN_CODE.getAuthor().getId()).isEqualTo(actual.getAuthor().getId());
        assertThat(CLEAN_CODE.getAuthor().getTitle()).isEqualTo(actual.getAuthor().getTitle());
        assertThat(CLEAN_CODE.getGenre().getId()).isEqualTo(actual.getGenre().getId());
        assertThat(CLEAN_CODE.getGenre().getTitle()).isEqualTo(actual.getGenre().getTitle());
    }

    @Transactional
    @DisplayName("Найти книгу по автору")
    @Test
    void getByAuthor(){

        List<Book> actual = repository.getByAuthor(UNCLE_BOBS_AUTHOR_ID);

        Author author = new Author(UNCLE_BOBS_AUTHOR_ID,UNCLE_BOB_TITLE);
        Genre genre = new Genre(PROGRAMMING_ID,PROGRAMMING_TITLE);
        Book expected2 = new Book(THE_CLEAN_CODER_ID,THE_CLEAN_CODER_TITLE,author,genre);
        List<Book> expected = Lists.newArrayList(CLEAN_CODE,expected2);
        expected.sort(Comparator.comparing(Book::getId));
        actual.sort(Comparator.comparing(Book::getId));
        assertThat(expected.size()).isEqualTo(actual.size());
        for(byte i = 0; i < 1; i++){
            assertEquals(expected.get(i).getId(),actual.get(i).getId());
            assertEquals(expected.get(i).getTitle(), actual.get(i).getTitle());
            assertEquals(expected.get(i).getAuthor().getId(), actual.get(i).getAuthor().getId());
            assertEquals(expected.get(i).getAuthor().getTitle(), actual.get(i).getAuthor().getTitle());
            assertEquals(expected.get(i).getGenre().getId(), actual.get(i).getGenre().getId());
            assertEquals(expected.get(i).getGenre().getTitle(), actual.get(i).getGenre().getTitle());
        }
    }

    @DisplayName(value = "Вернуть список из всех книг")
    @Test
    void getAll(){
        assertThat(repository.getAll().size()).isEqualTo(INITIAL_BOOKS_COUNT);
    }

    @Transactional
    @DisplayName(value = "Удалить книгу по идентификатору")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteById(){
        long count = repository.count();
        assertThat(ACTUAL_COUNT_OF_DELETED_ROWS_WITH_DELETING_BY_ID).isEqualTo(repository.deleteById(SUN_TSZY_BOOK_ID));
        assertThat(count - 1).isEqualTo(repository.count());
        assertThat(repository.getById(SUN_TSZY_BOOK_ID)).isNull();
    }

    @Transactional
    @DisplayName(value = "Удалить книгу по имени")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteByName(){
        long count = repository.count();
        assertThat(ACTUAL_COUNT_OF_DELETED_ROWS_WITH_DELETING_BY_ID)
                .isEqualTo(repository.deleteByName(SUN_TSZY_BOOK_TITLE));
        assertThat(count - 1).isEqualTo(repository.count());
        assertThat(repository.getById(SUN_TSZY_BOOK_ID)).isNull();
    }

    @DisplayName(value = "Найти книгу по названию и идентефикаторам автора и жанра")
    @Test
    void findByNameAndAuthorIdAndGenreId(){
        List<Book> actual = repository.findByNameAndAuthorIdAndGenreId(CLEAN_CO_TITLE,UNCLE_BOBS_AUTHOR_ID,PROGRAMMING_ID);
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getTitle()).isEqualTo(CLEAN_CODE_TITLE);
    }
}
