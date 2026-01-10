package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.tickets.TicketDto;
import com.example.demo.entity.types.TicketStatus;
import com.example.demo.exceptionHandlers.TicketException;
import com.example.demo.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @GetMapping("/all-tickets")
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        return supportService.getAllTickets();
    }

    @PatchMapping("/ticket/{id}/status")
    public ResponseEntity<ResponseDto> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        TicketStatus newStatus = TicketStatus.valueOf(body.get("status"));
        return supportService.updateStatus(id, newStatus);
    }

    @PostMapping("/assign/{id}")
    public ResponseEntity<ResponseDto> assignTicket(@PathVariable Long id, @RequestBody Map<String, String> body) {
        if (body.containsKey("userId")) {
            return supportService.assignTicket(id, Long.parseLong(body.get("userId")), true);
        } else if (body.containsKey("projectId")) {
            return supportService.assignTicket(id, Long.parseLong(body.get("projectId")), false);
        } else {
            throw new TicketException("Missing userId or projectId", HttpStatus.BAD_REQUEST);
        }
    }
}
