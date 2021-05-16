package com.whoisacat.edu.coursework.bookSharingProvider.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VisitingPlaceRDTO {

    @NotNull
    @NotEmpty
    private String country;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    private String street;

    @NotNull
    @NotEmpty
    private String house;

    @NotNull
    @NotEmpty
    private String orient;

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
