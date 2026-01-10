package com.example.demo.dto.auth;

public record SignUpResponseDto(
        Long user_id,
        String username,
        String message
) {
}
