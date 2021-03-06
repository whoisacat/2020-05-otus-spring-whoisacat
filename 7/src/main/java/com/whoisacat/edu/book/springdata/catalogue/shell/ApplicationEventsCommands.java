package com.whoisacat.edu.book.springdata.catalogue.shell;

import com.google.common.collect.Lists;
import com.whoisacat.edu.book.springdata.catalogue.domain.Author;
import com.whoisacat.edu.book.springdata.catalogue.domain.Book;
import com.whoisacat.edu.book.springdata.catalogue.domain.Genre;
import com.whoisacat.edu.book.springdata.catalogue.service.AuthorService;
import com.whoisacat.edu.book.springdata.catalogue.service.BookService;
import com.whoisacat.edu.book.springdata.catalogue.service.GenreService;
import com.whoisacat.edu.book.springdata.catalogue.service.exception.WHOAuthorAlreadyExists;
import com.whoisacat.edu.book.springdata.catalogue.service.exception.WHOBookAlreadyExists;
import com.whoisacat.edu.book.springdata.catalogue.service.exception.WHOGenreAlreadyExists;
import com.whoisacat.edu.book.springdata.catalogue.service.exception.WHORequestClientException;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class ApplicationEventsCommands{

    public static final String BOOK_ADDED_SUCCESSFULLY = "Успешно добавлена книга ";
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public ApplicationEventsCommands(BookService bookService,
                                     AuthorService authorService,
                                     GenreService genreService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(String userName) {
        if(!userName.isEmpty()){
            this.userName = userName;
            return String.format("Добро пожаловать: %s",userName);
        } else return "Нужно представиться";
    }

    @ShellMethod(value = "unLogin command", key = {"u","ulogin"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String ulogin(){
        String user = this.userName;
        this.userName = null;
        return String.format("Досвидания: %s",user);
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null? Availability.unavailable("Сначала залогиньтесь"): Availability.available();
    }

    @ShellMethod(value = "Вывести количество авторов", key = {"ca","count_authors"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public long countAuthors(){
        return authorService.getAuthorsCount();
    }

    @ShellMethod(value = "Вывести всех авторов", key = {"pa","print_authors"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String printAuthorsList(){
        return authorService.getAllAuthorsString();
    }

    @ShellMethod(value = "Найти автора по имени", key = {"fa","find_authors"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String findAuthorsList(String name){
        return authorService.findByName(name);
    }

    @ShellMethod(value = "Добавить автора. Введите имя автора. " +
            "Для имен из двух и более слов использовать ковычки", key = {"aa", "add_author"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String addAuthor(String name) {
        try{
            Author author = authorService.findByNameOrCreate(name);
            return author != null ? "Успешно добавлен автор " + author.getTitle() : "Автор не был добавлен";
        } catch(WHORequestClientException e){
            return e.getLocalizedMessage();
        } catch(Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Вывести количество жанров", key = {"cg","count_genres"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public long countGenre(){
        return genreService.getGenresCount();
    }

    @ShellMethod(value = "Вывести список всех жанров", key = {"pg","print_genres"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String printGenreList(){
        return genreService.getAllGenresString();
    }

    @ShellMethod(value = "Найти жанр по названию", key = {"fg","find_genres"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String findGenreList(String name){
        return genreService.findByName(name);
    }

    @ShellMethod(value = "Добавить жанр. Введите название жанра. " +
            "Для названий из двух и более слов использовать ковычки", key = {"ag", "add_genre"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String addGenre(String name) {
        try{
            Genre genre = genreService.findByNameOrCreate(name);
            return genre != null ? "Успешно добавлен жанр " + genre.getTitle() : "Жанр не был добавлен";
        } catch(WHORequestClientException e){
            return e.getLocalizedMessage();
        } catch(Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Добавить книгу. ВВедите название книги, автора, название жанра. " +
            "Для названий из двух и более слов использовать ковычки", key = {"ab", "add_book"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String addBook(String bookName,String author, String genre) {
        try{
            Book book = bookService.addBook(bookName,author,genre);
            return BOOK_ADDED_SUCCESSFULLY.concat(bookService.buildBooksString(Lists.newArrayList(book)));
        } catch(WHOAuthorAlreadyExists e){
            return "Выберите автора точнее, были найдены - "
                    .concat(authorService.findByName(author));
        } catch(WHOGenreAlreadyExists e){
            return "Выберите жанр точнее, были найдены - "
                    .concat(genreService.findByName(author));
        } catch(WHOBookAlreadyExists e){
            return "Выберите название книги точнее, были найдены - "
                    .concat(genreService.findByName(author));
        }
    }

    @ShellMethod(value = "Вывести общее количество книг", key = {"cb","count_books"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public long countBooks(){
        return bookService.getBooksCount();
    }

    @ShellMethod(value = "Вывести общее количество книг", key = {"pb","print_books"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String printBooks(){
        return bookService.getAllBooksString();
    }
}
