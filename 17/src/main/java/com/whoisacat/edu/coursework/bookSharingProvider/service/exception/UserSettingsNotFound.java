package com.whoisacat.edu.coursework.bookSharingProvider.service.exception;

public class UserSettingsNotFound extends WHORequestClientException {

    public static final String USER_SETTINGS_NOT_FOUND = "userSettingsNotFound";

    public UserSettingsNotFound() {
        super(USER_SETTINGS_NOT_FOUND);
    }
}
