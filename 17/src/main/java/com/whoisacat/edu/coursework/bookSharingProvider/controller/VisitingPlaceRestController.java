package com.whoisacat.edu.coursework.bookSharingProvider.controller;

import com.whoisacat.edu.coursework.bookSharingProvider.dto.*;
import com.whoisacat.edu.coursework.bookSharingProvider.service.UserService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.VisitingPlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VisitingPlaceRestController {

    private final  VisitingPlaceService visitingPlaceService;
    private final  UserService userService;

    public VisitingPlaceRestController(
            VisitingPlaceService visitingPlaceService,
            UserService userService) {
        this.visitingPlaceService = visitingPlaceService;
        this.userService = userService;
    }

    @GetMapping("visitingPlace")
    public ResponseEntity<Void> getDetails(@RequestParam(name = "country") String country,
            @RequestParam(name = "city") String city, @RequestParam(name = "street") String street,
            @RequestParam(name = "house") String house, @RequestParam(name = "orient") String  orient) {
        VisitingPlaceRDTO rdto = new VisitingPlaceRDTO();
        rdto.setCountry(country);
        rdto.setCity(city);
        rdto.setStreet(street);
        rdto.setHouse(house);
        rdto.setOrient(orient);
        visitingPlaceService.insert(rdto);
        return ResponseEntity.noContent().build();
    }
}
