package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.app.AppDto;
import com.example.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ResponseDto> publicRoute() {
        return ResponseEntity.ok(new ResponseDto("Health Check :: Public Route Accessible to everyone"));
    }

    @GetMapping("/apps")
    public ResponseEntity<List<AppDto>> getAllApps() {
        return adminService.getAllApps();
    }
}
