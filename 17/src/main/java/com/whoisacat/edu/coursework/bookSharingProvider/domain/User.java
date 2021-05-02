package com.whoisacat.edu.coursework.bookSharingProvider.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "who_user")
public class User {

    @Id
    @SequenceGenerator(name="user_seq", sequenceName = "user_seq",allocationSize = 1)
    @GeneratedValue(generator="user_seq")
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;//todo indexes, JPA validation

    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "who_user_id")
    private Set<Role> roles = new HashSet<>();
    private String firstName;
    private String lastName;

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
}
