package com.whoisacat.edu.book.batch.catalogue.domain.sql;

import com.whoisacat.edu.book.batch.catalogue.domain.Titled;

import javax.persistence.*;

@Entity
@Table(name = "author")
public class AuthorSQL implements Titled {

    @Id
    @SequenceGenerator(name="author_seq", sequenceName = "author_seq",allocationSize = 1)
    @GeneratedValue(generator="author_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "mongo_id")
    private String mongoId;

    public AuthorSQL(Long id, String title, String mongoId) {
        this.id = id;
        this.title = title;
        this.mongoId = mongoId;
    }

    public AuthorSQL(){
    }
    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getMongoId() {
        return mongoId;
    }
}
