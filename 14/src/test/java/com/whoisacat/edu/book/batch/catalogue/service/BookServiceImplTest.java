package com.whoisacat.edu.book.batch.catalogue.service;

import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Author;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Book;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Genre;
import com.whoisacat.edu.book.batch.catalogue.repository.mongo.BookMongoRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@DisplayName("Сервис для работы с книгами должен")
@Import(BookServiceImpl.class)
@DataMongoTest
class BookServiceImplTest {

    private static final Author AUTHOR_ODIN = new Author("1","odin");
    private static final Genre GENRE_OGIN = new Genre("1","ogin");
    private static final Book BOOK_ODIN = new Book("1","bodin",AUTHOR_ODIN,GENRE_OGIN);
    private static final String GENRE_STRING = "genreString";
    private static final String AUTHOR_STRING = "authorString";

    @Autowired
    private BookServiceImpl service;

    @MockBean private BookMongoRepository repository;
    @MockBean private AuthorService authorService;
    @MockBean private GenreService genreService;

}
