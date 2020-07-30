package com.whoisacat.edu.book.springdata.catalogue.domain;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book implements Titled{

    @Id
    @SequenceGenerator(name="book_seq", sequenceName = "book_seq",allocationSize = 1)
    @GeneratedValue(generator="book_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @ManyToOne
    private Genre genre;

    public Book(){
    }

    public Book(Long id,String title,Author author,Genre genre){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public Author getAuthor(){
        return author;
    }

    public Genre getGenre(){
        return genre;
    }
}
