package com.whoisacat.edu.book.springmvcini.catalogue.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Author;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Comment;
import com.whoisacat.edu.book.springmvcini.catalogue.domain.Genre;

@ChangeLog
public class InitialDatabaseChangelog{

    private Book bookArt;
    private Book bookCode;
    private Book bookCoder;
    private Book bookTDD;
    private Book bookSQL;
    private Book bookLinux;

    @ChangeSet(order = "000", id = "dropDB", author = "whoisacat", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "whoisacat",runAlways = true)
    public void insertAuthors(MongockTemplate template) {//можно прямо репу
        Author sunTszy = template.save(new Author("Сунь-Цзы"));
        Author bek = template.save(new Author("Кент Бек"));
        Author bob = template.save(new Author("Роберт Мартин"));
        Author sql = template.save(new Author("Л.Н. Полякова"));
        Author linuxxx = template.save(new Author("Дмитрий Кетов"));
        template.save(new Author("Дмитрий Быков"));

        Genre prog = template.save(new Genre("Программирование"));
        Genre art = template.save(new Genre("Историческая художка"));
        template.save(new Genre("Херь несусветная"));

        bookArt = template.save(new Book("Исскуство войны",sunTszy,art));
        bookCode = template.save(new Book("Чистый код",bob,prog));
        bookCoder = template.save(new Book("Идеальный программист",bob,prog));
        bookTDD = template.save(new Book("Разработка через тестирование",bek,prog));
        bookSQL = template.save(new Book("Основы SQL",sql,prog));
        bookLinux = template.save(new Book("Внутреннее устройство LINUX",linuxxx,prog));
    }

    @ChangeSet(order = "002", id = "addComments", author = "whoisacat", runAlways = true)
    public void addComments(MongockTemplate template){
        template.save(new Comment("Времяубиватель",
                "Книга для любителей слова исскуство в названии книги. Или для тех, кто знает, что Сунь Цзы это не Лао Цзы",
                bookArt));
        template.save(new Comment("Понравилось",
                "Действительно важная книга о многотысячелетней мудрости китайского народа",
                bookArt));
        template.save(new Comment("Маст рид",
                "Классика по качеству кода",
                bookCode));
        template.save(new Comment("Читать всем программистам",
                "Неустаревающий стандарт качества для программиста",
                bookCode));
        template.save(new Comment("фигня",
                "Ох и устарел этот ваш дядя боб",
                bookCode));
        template.save(new Comment("Хвала Линусу Торвалдсу",
                "Скучно, но нужно. Хотя и не очень то и скучно. Но очень нужно. И качественно",
                bookLinux));
        template.save(new Comment("Прочитано",
                "Уже забыл о чем, но читал и не осуждаю",
                bookCoder));
    }
}
