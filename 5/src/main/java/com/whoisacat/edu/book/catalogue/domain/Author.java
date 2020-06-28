package com.whoisacat.edu.book.catalogue.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Author implements Named{

    private final long id;
    private final String name;
    private final Set<Book> books = new HashSet<>();

    public Author(long id,String name,List<Book> books){
        this.id = id;
        this.name = name;
        this.books.addAll(books);
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Set<Book> getBooks(){
        return books;
    }
}
