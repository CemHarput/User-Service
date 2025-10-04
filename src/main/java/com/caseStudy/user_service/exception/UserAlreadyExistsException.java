package com.caseStudy.user_service.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String email) {
        super("User already exists with email: " + email);
    }
}
