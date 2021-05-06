package com.whoisacat.edu.coursework.bookSharingProvider.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "who_user",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"}),
        indexes = {
                @Index(columnList = "id"),
                @Index(columnList = "email")})
public class User {

    @Id
    @SequenceGenerator(name="user_seq", sequenceName = "user_seq",allocationSize = 1)
    @GeneratedValue(generator="user_seq")
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "who_user_id")
    private Set<Role> roles = new HashSet<>();

    private String firstName;

    private String lastName;

    @OneToMany
    @JoinColumn(name = "who_user_id")
    private List<VisitingPlace> visitingPlaces = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "who_user_id")
    private List<Book> books = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authList = new HashSet<GrantedAuthority>();

        for (Role role : this.getRoles()) {
            authList.add(new SimpleGrantedAuthority(role.getRoleName().name()));
        }
        return authList;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<VisitingPlace> getVisitingPlaces() {
        return visitingPlaces;
    }

    public void setVisitingPlaces(
            List<VisitingPlace> visitingPlaces) {
        this.visitingPlaces = visitingPlaces;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
