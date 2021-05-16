package com.whoisacat.edu.coursework.bookSharingProvider.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class EditUserDTO {

    @NotNull
    @NotEmpty
    private final String firstName;

    @NotNull
    @NotEmpty
    private final String lastName;

    private final String email;

    private final List<VisitingPlaceDTO> places;

    private final String readonly;

    public EditUserDTO(String firstName, String lastName, String email,
            List<VisitingPlaceDTO> places, String readonly) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.places = places;
        this.readonly = readonly;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<VisitingPlaceDTO> getPlaces() {
        return places;
    }

    public String getReadonly() {
        return readonly;
    }
}
