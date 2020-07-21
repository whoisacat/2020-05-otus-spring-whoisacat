package com.whoisacat.edu.book.jpa.catalogue.repository;

import com.whoisacat.edu.book.jpa.catalogue.domain.Author;
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

import javax.persistence.PersistenceException;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий для работы с авторами должен")
@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(AuthorRepositoryImpl.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class AuthorRepositoryImplTest{

    public static final long INITIAL_AUTHORS_COUNT = 6L;
    public static final int INITIAL_AUTHORS_COUNT_PLUS_ONE = 7;
    public static final int INITIAL_AUTHORS_COUNT_PLUS_TWO = 8;
    public static final int LATEST_INITIAL_AUTHOR_ID = 6;
    public static final int UNCLE_BOBS_ID = 3;
    public static final String UNCLE_BOBS_TITLE = "Роберт Мартин";
    public static final int DMITRIYS_AUTHORS_COUNT = 2;
    public static final String DMITRIYS_FIRST_NAME_STRING = "Дмитрий";
    public static final int ACTUAL_COUNT_OF_DELETED_ROWS_WITH_DELETING_BY_ID = 1;
    public static final long INITIAL_AUTHORS_COUNT_MINUS_ONE = 5L;
    @Autowired
    private AuthorRepositoryImpl repository;

    @DisplayName(value = "правильно посчитать авторов")
    @Test
    void countAuthorsTest(){
        assertThat(repository.count()).isEqualTo(6);
    }

    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "вставить нового автора")
    @Test
    void insertAuthorsTest(){
        assertThat(INITIAL_AUTHORS_COUNT).isEqualTo(repository.count());
        repository.save(new Author(null,"some new author"));
        assertThat(repository.count()).isEqualTo(INITIAL_AUTHORS_COUNT_PLUS_ONE);
    }

    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "вставить нового автора после удаления автора")
    @Test
    void insertAuthorAfterDeletingAuthorAfterInsertingAuthorTest(){
        repository.save(new Author(null,"some new author1"));
        repository.save(new Author(null,"some new author2"));
        assertThat(repository.count()).isEqualTo(INITIAL_AUTHORS_COUNT_PLUS_TWO);
        repository.deleteById(LATEST_INITIAL_AUTHOR_ID);
        assertThat(repository.count()).isEqualTo(INITIAL_AUTHORS_COUNT_PLUS_ONE);
        repository.save(new Author(null,"some new author3"));
        assertThat(repository.count()).isEqualTo(INITIAL_AUTHORS_COUNT_PLUS_TWO);
    }

    @DisplayName(value = "найти одного автора по идентефикатору")
    @Test
    void getAuthorByIdTest(){
        Author author = repository.getById(UNCLE_BOBS_ID);
        assertThat(author.getId()).isEqualTo(UNCLE_BOBS_ID);
    }

    @DisplayName(value = "найти автора по идентефикатору")
    @Test
    void mapAuthorsNameByIdTest(){
        Author author = repository.getById(UNCLE_BOBS_ID);
        assertThat(author.getTitle()).isEqualTo(UNCLE_BOBS_TITLE);
    }

    @DisplayName(value = "найти двух авторов по имени")
    @Test
    void getAuthorByNameTest(){
        List<Author> authors = repository.getByName(DMITRIYS_FIRST_NAME_STRING);
        assertEquals(authors.size(),DMITRIYS_AUTHORS_COUNT);
    }

    @DisplayName(value = "найти всех авторов")
    @Test
    void getAllAuthorsTest(){
        List<Author> list = repository.getAll();
        assertThat(list.size() == INITIAL_AUTHORS_COUNT);
        list.sort(Comparator.comparing(Author::getId));
        int UNCLE_BOBS_SORTED_NUMBER = 2;
        assertThat(list.get(UNCLE_BOBS_SORTED_NUMBER).getTitle()).isEqualTo("Роберт Мартин");
    }

    @Transactional
    @DisplayName(value = "выбросить исключение так как есть связи")
    @Test
    void noDeletingAuthorByIdTest(){
        assertThrows(PersistenceException.class,() -> repository.deleteById(1));
    }

    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(value = "удалить автора по идентефикатору")
    @Test
    void deleteAuthorByIdTest(){
        assertThat(ACTUAL_COUNT_OF_DELETED_ROWS_WITH_DELETING_BY_ID)
                .isEqualTo(repository.deleteById(INITIAL_AUTHORS_COUNT));
        assertThat(INITIAL_AUTHORS_COUNT_MINUS_ONE).isEqualTo(repository.count());
    }
}
