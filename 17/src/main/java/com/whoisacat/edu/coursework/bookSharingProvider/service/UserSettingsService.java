package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.UserSettings;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.UserSettingsRepository;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHODataAccessException;
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

    @HystrixCommand(commandKey = "getUserSettings", fallbackMethod = "buildFallbackRows")
    public UserSettings getUserSettings() {
        String username = getUsernameFromSecurityContext();
        return repository.findByUserUsername(username);
    }

    private String getUsernameFromSecurityContext() {
        SecurityContext ctxt = SecurityContextHolder.getContext();
        String username = ((WHOUserPrincipal) ctxt.getAuthentication().getPrincipal()).getUsername();
        return username;
    }

    @HystrixCommand(commandKey = "setRowsPerPage", fallbackMethod = "buildFallbackRows")
    public void setRowsPerPage(Integer rowsPerPage) {
        String username = getUsernameFromSecurityContext();
        UserSettings userSettings = repository.findByUserUsername(username);
        userSettings.setRowsPerPage(rowsPerPage);
        repository.save(userSettings);
    }

    public void buildFallbackRows() {
        throw new WHODataAccessException("Database is not available");
    }
}
