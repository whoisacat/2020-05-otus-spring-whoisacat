package com.whoisacat.edu.coursework.bookSharingProvider.domain;

import javax.persistence.*;

@Entity
@Table(name = "who_role")
public class Role {

    @Id
    @SequenceGenerator(name="who_role_seq", sequenceName = "who_role_seq",allocationSize = 1)
    @GeneratedValue(generator="who_role_seq")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ROLES roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ROLES getRoleName() {
        return roleName;
    }

    public void setRoleName(ROLES name) {
        this.roleName = name;
    }
}
