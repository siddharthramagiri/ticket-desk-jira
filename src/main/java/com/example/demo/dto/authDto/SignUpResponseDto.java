package com.example.demo.dto.authDto;

public record SignUpResponseDto(
        Long user_id,
        String username,
        String message
) {
}
