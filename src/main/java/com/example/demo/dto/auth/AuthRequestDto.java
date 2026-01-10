package com.example.demo.dto.auth;

public record AuthRequestDto(
        String email,
        String password
) {
}
