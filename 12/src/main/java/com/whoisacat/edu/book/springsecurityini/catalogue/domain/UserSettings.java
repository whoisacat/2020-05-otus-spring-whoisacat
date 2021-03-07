package com.whoisacat.edu.book.springsecurityini.catalogue.domain;

import javax.persistence.*;

@Entity
public class UserSettings {

    @Id
    @SequenceGenerator(name="genre_seq", sequenceName = "genre_seq",allocationSize = 1)
    @GeneratedValue(generator="genre_seq")
    private Long id;
    @Column(name = "rowsPerPage")
    private Integer rowsPerPage;
    @JoinColumn
    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
