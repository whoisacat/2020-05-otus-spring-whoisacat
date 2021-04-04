package com.whoisacat.edu.book.batch.catalogue.domain.sql;

import com.whoisacat.edu.book.batch.catalogue.domain.Titled;

import javax.persistence.*;

@Entity
@Table(name = "genre",uniqueConstraints={@UniqueConstraint(columnNames={"title"})})
public class GenreSQL implements Titled {

    @Id
    @SequenceGenerator(name="genre_seq", sequenceName = "genre_seq",allocationSize = 1)
    @GeneratedValue(generator="genre_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    private String mongoId;

    public GenreSQL(){
    }

    public GenreSQL(Long id, String title, String mongoId) {
        this.id = id;
        this.title = title;
        this.mongoId = mongoId;
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
