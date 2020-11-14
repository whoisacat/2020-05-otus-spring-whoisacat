package com.whoisacat.edu.book.springmvcini.catalogue.service;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.UserSettings;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSettingsService{

    private UserSettings userSettings;

    public UserSettingsService(){
        this.userSettings = new UserSettings();
        this.userSettings.setRowsPerPage(10);
    }

    public UserSettings getUserSettings(){
        return userSettings;
    }
}
