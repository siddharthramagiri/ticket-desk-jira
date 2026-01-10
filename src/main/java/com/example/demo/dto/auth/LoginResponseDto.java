package com.example.demo.dto.auth;

import com.example.demo.entity.types.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record LoginResponseDto(
        String token,
        Long id,
        String email,
        Set<Role> roles
) {
}
