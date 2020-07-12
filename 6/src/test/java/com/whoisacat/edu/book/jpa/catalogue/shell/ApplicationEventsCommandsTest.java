package com.whoisacat.edu.book.jpa.catalogue.shell;

import com.whoisacat.edu.book.jpa.catalogue.domain.Author;
import com.whoisacat.edu.book.jpa.catalogue.domain.Book;
import com.whoisacat.edu.book.jpa.catalogue.domain.Genre;
import com.whoisacat.edu.book.jpa.catalogue.service.AuthorServiceSimple;
import com.whoisacat.edu.book.jpa.catalogue.service.BookServiceSimple;
import com.whoisacat.edu.book.jpa.catalogue.service.GenreService;
import com.whoisacat.edu.book.jpa.catalogue.service.exception.WHORequestClientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static com.whoisacat.edu.book.jpa.catalogue.shell.ApplicationEventsCommands.BOOK_ADDED_SUCCESSFULLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName(value = "Тест команд  shell должен")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class ApplicationEventsCommandsTest{

    private static final String LOGIN = "l тестировщик";
    public static final String SPRING_SHELL_EXCEPTION_MESSAGE =
            "org.springframework.shell.ParameterMissingResolutionException: Parameter '--user-name string' should be specified";
    private static final String OUT = "u";
    public static final String ADD_BOOK = "ab некоторая новая книга";
    public static final String LOGIN_FIRST_MESSAGE =
            "org.springframework.shell.CommandNotCurrentlyAvailable: Command 'ab' exists but is not currently available because Сначала залогиньтесь";
    public static final String TRUE_LA_LA = "true-la-la";
    public static final Author AUTHOR = new Author((1L),TRUE_LA_LA,new ArrayList<>());
    public static final Genre GENRE = new Genre((1L),TRUE_LA_LA);
    public static final Book BOOK = new Book((1L),TRUE_LA_LA,AUTHOR,GENRE);

    @Autowired Shell shell;
    @MockBean AuthorServiceSimple authorService;
    @MockBean GenreService genreService;
    @MockBean BookServiceSimple bookService;

    @DisplayName(value = "Должен отобразить приветствие после логина")
    @Test
    void login(){
        assertThat(shell.evaluate(() -> LOGIN)).isEqualTo("Добро пожаловать: тестировщик");
    }

    @DisplayName(value = "Должен сказать, что имя пользователя обязательно")
    @Test
    void usernameIsRequired(){
        Object answer = shell.evaluate(() -> "l");
        assertThat(answer.toString()).isEqualTo(SPRING_SHELL_EXCEPTION_MESSAGE);
    }

    @DisplayName(value = "Должен попращаться после дизлогина")
    @Test
    void ulogin(){
        shell.evaluate(() -> LOGIN);
        assertThat(shell.evaluate(() -> OUT)).isEqualTo("Досвидания: тестировщик");
    }

    @DisplayName(value = "не должен выполнить команду без логина")
    @Test
    void dontDoAnythingWithoutLogging(){
        assertThat(shell.evaluate(()->ADD_BOOK).toString()).isEqualTo(LOGIN_FIRST_MESSAGE);
    }

    @DisplayName(value = "Должен вывести что насчитает сервис авторов")
    @Test
    void countAuthors(){
        shell.evaluate(() -> LOGIN);
        when(authorService.getAuthorsCount()).thenReturn(42L);
        assertThat(shell.evaluate(() -> "ca")).isEqualTo(42L);
    }

    @DisplayName(value = "Должен вывести строку списка авторов, которую вернет сервис авторов")
    @Test
    void printAuthorsList(){
        shell.evaluate(() -> LOGIN);
        when(authorService.getAllAuthorsString()).thenReturn(TRUE_LA_LA);
        assertThat(shell.evaluate(() -> "pa")).isEqualTo(TRUE_LA_LA);
    }

    @DisplayName("Вернуть строку из сервиса с найденными авторами")
    @Test
    void findAuthorsList(){
        shell.evaluate(() -> LOGIN);
        when(authorService.findByName("name")).thenReturn(TRUE_LA_LA);
        assertThat(shell.evaluate(() -> "fa name")).isEqualTo(TRUE_LA_LA);
    }

    @Test
    void addAuthor(){
        shell.evaluate(() -> LOGIN);
        when(authorService.findByNameOrCreate(TRUE_LA_LA)).thenReturn(AUTHOR);
        assertThat(shell.evaluate(() -> "aa true-la-la")).isEqualTo("Успешно добавлен автор " + TRUE_LA_LA);
    }

    @Test
    void foundedMoreThanOneAuthorWhenAdding(){
        shell.evaluate(() -> LOGIN);
        when(authorService.findByNameOrCreate(TRUE_LA_LA)).thenThrow(WHORequestClientException.class);
        assertThat(shell.evaluate(() -> "aa true-la-la")).isEqualTo(null);
    }

    @Test
    void countGenre(){
        shell.evaluate(() -> LOGIN);
        when(genreService.getGenresCount()).thenReturn(42L);
        assertThat(shell.evaluate(() -> "cg")).isEqualTo(42L);
    }

    @Test
    void printGenreList(){
        shell.evaluate(() -> LOGIN);
        when(genreService.getAllGenresString()).thenReturn(TRUE_LA_LA);
        assertThat(shell.evaluate(() -> "pg")).isEqualTo(TRUE_LA_LA);
    }

    @Test
    void findGenreList(){
        shell.evaluate(() -> LOGIN);
        when(genreService.findByName("name")).thenReturn(TRUE_LA_LA);
        assertThat(shell.evaluate(() -> "fg name")).isEqualTo(TRUE_LA_LA);
    }

    @Test
    void addGenre(){
        shell.evaluate(() -> LOGIN);
        when(genreService.findByNameOrCreate(TRUE_LA_LA)).thenReturn(GENRE);
        assertThat(shell.evaluate(() -> "ag true-la-la")).isEqualTo("Успешно добавлен жанр " + TRUE_LA_LA);
    }

    @Test
    void addBook(){
        shell.evaluate(() -> LOGIN);
        when(bookService.addBook(TRUE_LA_LA,TRUE_LA_LA,TRUE_LA_LA)).thenReturn(BOOK);
        when(bookService.buildBooksString(any(List.class))).thenReturn("BOOK");
        assertThat(shell.evaluate(() -> "ab " + TRUE_LA_LA + " " + TRUE_LA_LA + " " + TRUE_LA_LA))
                .isEqualTo(BOOK_ADDED_SUCCESSFULLY.concat("BOOK"));
    }

    @Test
    void countBooks(){
        shell.evaluate(() -> LOGIN);
        when(bookService.getBooksCount()).thenReturn(42L);
        assertThat(shell.evaluate(() -> "cb")).isEqualTo(42L);
    }
}
