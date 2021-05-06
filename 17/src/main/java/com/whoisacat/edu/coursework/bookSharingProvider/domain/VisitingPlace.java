package com.whoisacat.edu.coursework.bookSharingProvider.domain;

import javax.persistence.*;

@Entity
public class VisitingPlace {

    public static final String VISITING_PLACE_SEQ = "visiting_place_seq";
    @Id
    @SequenceGenerator(name=VISITING_PLACE_SEQ, sequenceName = VISITING_PLACE_SEQ,allocationSize = 1)
    @GeneratedValue(generator= VISITING_PLACE_SEQ)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String house;

    private String orient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getOrient() {
        return orient;
    }

    public void setOrient(String orient) {
        this.orient = orient;
    }
}
