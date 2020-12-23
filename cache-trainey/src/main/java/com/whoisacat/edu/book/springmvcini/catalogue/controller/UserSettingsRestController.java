package com.whoisacat.edu.book.springmvcini.catalogue.controller;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.UserSettings;
import com.whoisacat.edu.book.springmvcini.catalogue.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "userSettings")
public class UserSettingsRestController{

    public static int PAGE_SIZE = 10;

    private final UserSettingsService service;

    @Autowired
    public UserSettingsRestController(UserSettingsService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<UserSettings> getPsettings(){
        return ResponseEntity.ok(service.getUserSettings());
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> setPsettings(@RequestParam(name = "rowsPerPage") int rowsPerPage){
        service.getUserSettings().setRowsPerPage(rowsPerPage);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
