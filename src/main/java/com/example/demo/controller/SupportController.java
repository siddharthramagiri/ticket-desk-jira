package com.example.demo.controller;

import com.example.demo.dto.ticketDto.TicketDto;
import com.example.demo.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @GetMapping("/all-tickets")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        return supportService.getAllTickets();
    }
}
