package com.example.demo.exceptionHandlers;

import org.springframework.http.HttpStatus;

public class TicketException extends ApiException {
    public TicketException(String message, HttpStatus status) {
        super(message, status);
    }
}
