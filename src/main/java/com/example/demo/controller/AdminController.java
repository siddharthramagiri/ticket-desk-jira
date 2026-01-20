package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ResponseDto> adminRoute() {
        return ResponseEntity.ok(new ResponseDto("Admin Route Accessed with Admin Credentials"));
    }

    @PutMapping("/update/{id}/role")
    public ResponseEntity<ResponseDto> assignRole(@PathVariable Long id, @RequestParam String role) {
        return adminService.assignRole(id, role);
    }

    @GetMapping("/all-users")
    public ResponseEntity<Set<UserDto>> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PostMapping("/make-admin/{id}")
    public ResponseEntity<ResponseDto> makeAdmin(@PathVariable Long id) {
        return adminService.makeAdmin(id);
    }

    @PostMapping("/new-app/{app_name}")
    public ResponseEntity<ResponseDto> createApp(@PathVariable String app_name) {
        return adminService.createApp(app_name);
    }

}
