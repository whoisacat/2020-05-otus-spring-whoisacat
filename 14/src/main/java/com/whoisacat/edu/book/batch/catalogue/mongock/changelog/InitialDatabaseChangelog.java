package com.whoisacat.edu.book.batch.catalogue.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Author;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Book;
import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Genre;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeLog
public class InitialDatabaseChangelog{


    @ChangeSet(order = "000", id = "dropDB", author = "whoisacat", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "whoisacat",runAlways = true)
    public void insertAuthors(MongoTemplate template) {
        Author sunTszy = template.save(new Author("Сунь-Цзы"));
        Author bek = template.save(new Author("Кент Бек"));
        Author bob = template.save(new Author("Роберт Мартин"));
        Author sql = template.save(new Author("Л.Н. Полякова"));
        Author linuxxx = template.save(new Author("Дмитрий Кетов"));
        template.save(new Author("Дмитрий Быков"));

        Genre prog = template.save(new Genre("Программирование"));
        Genre art = template.save(new Genre("Историческая художка"));
        template.save(new Genre("Херь несусветная"));

        template.save(new Book("Исскуство войны",sunTszy,art));
        template.save(new Book("Чистый код",bob,prog));
        template.save(new Book("Идеальный программист",bob,prog));
        template.save(new Book("Разработка через тестирование",bek,prog));
        template.save(new Book("Основы SQL",sql,prog));
        template.save(new Book("Внутреннее устройство LINUX",linuxxx,prog));
    }
}
