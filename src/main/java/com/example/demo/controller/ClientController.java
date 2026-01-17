package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.tickets.NewTicketDto;
import com.example.demo.dto.tickets.TicketDto;
import com.example.demo.entity.Ticket;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/my")
    public ResponseEntity<List<TicketDto>> getTicketsCreatedByMe() {
        return clientService.getTicketsCreatedByMe();
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addTicket(@RequestBody NewTicketDto ticketDto) {
        return clientService.addTicket(ticketDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteTicket(@PathVariable Long id) {
        return clientService.deleteTicket(id);
    }


    @GetMapping("/ticket/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        return clientService.getTicket(id);
    }

}
