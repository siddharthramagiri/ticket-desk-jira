package com.example.demo.dto.projects;

import com.example.demo.dto.users.UserDto;

import java.util.List;

public record NewProjectDto (
        String name,
        List<UserDto> users
)
{}
