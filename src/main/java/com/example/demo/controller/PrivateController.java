package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.projects.ProjectDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.types.Role;
import com.example.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/private")
@RequiredArgsConstructor
public class PrivateController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ResponseDto> privateRoute() {
        return ResponseEntity.ok(new ResponseDto("Private Route Accessed by Login"));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> fetchUsersByRole(@RequestParam String role) {
        return adminService.fetchUsersByRole(Role.valueOf(role));
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDto>> fetchProjects() {
        return adminService.fetchProjects();
    }
}
