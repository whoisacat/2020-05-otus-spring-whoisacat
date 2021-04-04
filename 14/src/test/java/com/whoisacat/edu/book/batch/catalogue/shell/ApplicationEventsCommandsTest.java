package com.whoisacat.edu.book.batch.catalogue.shell;

import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Author;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Book;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Genre;
import com.whoisacat.edu.book.batch.catalogue.service.AuthorServiceImpl;
import com.whoisacat.edu.book.batch.catalogue.service.BookServiceImpl;
import com.whoisacat.edu.book.batch.catalogue.service.GenreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

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
    public static final String START_MIGRATION = "sm";
    public static final String LOGIN_FIRST_MESSAGE =
            "org.springframework.shell.CommandNotCurrentlyAvailable: Command 'sm' exists but is not currently available because Сначала залогиньтесь";

    @Autowired Shell shell;
    @MockBean AuthorServiceImpl authorService;
    @MockBean GenreService genreService;
    @MockBean BookServiceImpl bookService;

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
        assertThat(shell.evaluate(()-> START_MIGRATION).toString()).isEqualTo(LOGIN_FIRST_MESSAGE);
    }
}
