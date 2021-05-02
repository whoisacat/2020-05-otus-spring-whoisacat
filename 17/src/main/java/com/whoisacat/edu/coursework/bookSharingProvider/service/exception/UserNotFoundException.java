package com.whoisacat.edu.coursework.bookSharingProvider.service.exception;

public class UserNotFoundException extends WHORequestClientException {

    public static final String USER_NOT_FOUND = "userNotFound";

    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}
