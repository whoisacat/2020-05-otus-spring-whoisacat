package com.whoisacat.edu.coursework.bookSharingProvider.dto;


public class VisitingPlaceDTO {

    private final String country;
    private final String city;
    private final String street;
    private final String house;
    private final String orient;

    public VisitingPlaceDTO(String country, String city, String street, String house, String orient) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.orient = orient;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getOrient() {
        return orient;
    }
}
