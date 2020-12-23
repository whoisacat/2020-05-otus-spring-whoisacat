package com.whoisacat.edu.book.springmvcini.catalogue.repository;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    public static final String BOOKS = "books";
    private final BookRepository repository;

    public BookRepositoryCustomImpl(@Lazy BookRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = BOOKS, key = "#book.title + #book.author.title")
    public Book createObject(Book book) {
        System.out.println("public Book saveObject(Book book)");
        return repository.save(book);
    }

    @CachePut(value = BOOKS, key = "#book.title")
    public Book updateObject(Book book) {
        System.out.println("public Book saveObject(Book book)");
        return repository.save(book);
    }

    @Cacheable(value = BOOKS, key = "#title", unless = "#result == null")
    public Book findOne(int title) {
        System.out.println("findOne(" + title + ");");
        Book book = repository.getByTitleLike("" + title);
        if (book != null){
            System.out.println("founded " + book.getTitle() + " title = " + book.getId());
        }
        return book;
    }
}
