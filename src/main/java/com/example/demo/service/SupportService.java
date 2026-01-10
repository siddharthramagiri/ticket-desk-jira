package com.example.demo.service;

import com.example.demo.dto.ticketDto.TicketDto;
import com.example.demo.entity.Ticket;
import com.example.demo.exceptionHandlers.TicketException;
import com.example.demo.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportService {

    private final TicketRepository ticketRepository;

    public ResponseEntity<List<TicketDto>> getAllTickets() {
        try {
            List<Ticket> tickets = ticketRepository.findAll();
            List<TicketDto> ticketsDto = tickets.stream()
                    .map(TicketDto::new).toList();

            return ResponseEntity.ok(ticketsDto);
        } catch (Exception e) {
            throw new TicketException("Could not fetch Tickets", HttpStatus.NOT_FOUND);
        }
    }
}
