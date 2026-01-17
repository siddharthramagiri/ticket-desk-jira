package com.example.demo.exceptionHandlers;

import org.springframework.http.HttpStatus;

public class DeveloperException extends ApiException {
    public DeveloperException(String message, HttpStatus status) {
        super(message, status);
    }
}
