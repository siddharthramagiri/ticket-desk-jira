package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ResponseDto> adminRoute() {
        return ResponseEntity.ok(new ResponseDto("Admin Route Accessed with Admin Credentials"));
    }

    @PostMapping("/role/{id}/{role}")
    public ResponseEntity<ResponseDto> assignRole(@PathVariable Long id, @PathVariable String role) {
        return adminService.assignRole(id, role);
    }

    @PostMapping("/make-admin/{id}")
    public ResponseEntity<ResponseDto> makeAdmin(@PathVariable Long id) {
        return adminService.makeAdmin(id);
    }

}
