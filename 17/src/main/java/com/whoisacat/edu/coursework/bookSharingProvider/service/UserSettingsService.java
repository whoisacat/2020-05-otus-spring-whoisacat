package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.User;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.UserSettings;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.UserSettingsRepository;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.UserSettingsNotFound;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHODataAccessException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserSettingsService {

    public static final int INITIAL_ROWS_PER_PAGE_QUANTITY = 3;
    private final UserSettingsRepository repository;

    public UserSettingsService(
            UserSettingsRepository repository){
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Optional<UserSettings> getUserSettings() {
        String username = getUsernameFromSecurityContext();
        return repository.findByUserEmail(username);
    }

    private String getUsernameFromSecurityContext() {
        SecurityContext ctxt = SecurityContextHolder.getContext();
        String username = ((WHOUserPrincipal) ctxt.getAuthentication().getPrincipal()).getUsername();
        return username;
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        String username = getUsernameFromSecurityContext();
        UserSettings userSettings = repository.findByUserEmail(username).orElseThrow(UserSettingsNotFound::new);
        userSettings.setRowsPerPage(rowsPerPage);
        repository.save(userSettings);
    }

    public void create(User user) {
        UserSettings userSettings = new UserSettings();
        userSettings.setUser(user);
        userSettings.setRowsPerPage(INITIAL_ROWS_PER_PAGE_QUANTITY);
        repository.save(userSettings);
    }
}
