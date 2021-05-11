package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.User;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.UserSettings;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.UserSettingsRepository;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.UserSettingsNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserSettingsService {

    public static final int INITIAL_ROWS_PER_PAGE_QUANTITY = 3;
    private final UserSettingsRepository repository;
    private final UserService userService;

    public UserSettingsService(
            UserSettingsRepository repository,
            UserService userService){
        this.repository = repository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Optional<UserSettings> getUserSettings() {
        String username = userService.getUsernameFromSecurityContext();
        return repository.findByUserEmail(username);
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        String username = userService.getUsernameFromSecurityContext();
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
