package com.example.demo.exceptionHandlers;

import org.springframework.http.HttpStatus;

public class UserException extends ApiException {
    public UserException(String message, HttpStatus status) {
        super(message, status);
    }
}
