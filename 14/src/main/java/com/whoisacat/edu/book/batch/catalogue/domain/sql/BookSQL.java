package com.whoisacat.edu.book.batch.catalogue.domain.sql;

import com.whoisacat.edu.book.batch.catalogue.domain.Titled;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class BookSQL implements Titled {

    @Id
    @SequenceGenerator(name="book_seq", sequenceName = "book_seq",allocationSize = 1)
    @GeneratedValue(generator="book_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthorSQL author;

    @ManyToOne
    private GenreSQL genre;

    public BookSQL(){
    }

    public BookSQL(Long id,String title,AuthorSQL author,GenreSQL genre){
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

    public AuthorSQL getAuthor(){
        return author;
    }

    public GenreSQL getGenre(){
        return genre;
    }
}
