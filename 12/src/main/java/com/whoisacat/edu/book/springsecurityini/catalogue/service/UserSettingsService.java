package com.whoisacat.edu.book.springsecurityini.catalogue.service;

import com.whoisacat.edu.book.springsecurityini.catalogue.domain.User;
import com.whoisacat.edu.book.springsecurityini.catalogue.domain.UserSettings;
import com.whoisacat.edu.book.springsecurityini.catalogue.repository.UserSettingsRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsService {

    private final UserSettingsRepository repository;

    public UserSettingsService(
            UserSettingsRepository repository){
        this.repository = repository;
    }

    public UserSettings getUserSettings() {
        String username = getUsernameFromSecurityContext();
        return repository.findByUserUsername(username);
    }

    private String getUsernameFromSecurityContext() {
        SecurityContext ctxt = SecurityContextHolder.getContext();
        String username = ((WHOUserPrincipal) ctxt.getAuthentication().getPrincipal()).getUsername();
        return username;
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        String username = getUsernameFromSecurityContext();
        UserSettings userSettings = repository.findByUserUsername(username);
        userSettings.setRowsPerPage(rowsPerPage);
        repository.save(userSettings);
    }
}
