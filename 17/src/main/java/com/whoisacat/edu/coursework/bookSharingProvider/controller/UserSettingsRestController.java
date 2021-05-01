package com.whoisacat.edu.coursework.bookSharingProvider.controller;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.UserSettings;
import com.whoisacat.edu.coursework.bookSharingProvider.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserSettingsRestController{

    private final UserSettingsService service;

    @Autowired
    public UserSettingsRestController(UserSettingsService service) {
        this.service = service;
    }

    @GetMapping("userSettings")
    public ResponseEntity<UserSettings> getPsettings(){
        return ResponseEntity.ok(service.getUserSettings());
    }

    @PostMapping(path = "userSettings")
    public ResponseEntity<Void> setPsettings(@RequestParam(name = "rowsPerPage") int rowsPerPage){
        service.setRowsPerPage(rowsPerPage);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
