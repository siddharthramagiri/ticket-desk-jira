package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.ticketDto.NewTicketDto;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.entity.types.Priority;
import com.example.demo.exceptionHandlers.TicketException;
import com.example.demo.exceptionHandlers.UserException;
import com.example.demo.repository.TicketRepository;
import com.example.demo.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

//    private UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public ResponseEntity<ResponseDto> addTicket(NewTicketDto ticketDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new UserException("Authentication Failed", HttpStatus.BAD_REQUEST);
        }

        try {
            User user = (User) authentication.getPrincipal();

            Ticket ticket = Ticket.builder()
                    .title(ticketDto.title())
                    .applicationName(ticketDto.applicationName())
                    .description(ticketDto.description())
                    .deadLine(ticketDto.deadLine())
                    .build();

            ticket.setPriority(Priority.valueOf(ticketDto.priority().toUpperCase()));

            ticket.setCreatedBy(user);
            ticketRepository.save(ticket);
            return ResponseEntity.ok(new ResponseDto("Issue Sent Successfully"));

        } catch (Exception e) {
            log.info("Error in creating Ticket: ", e);
            throw new TicketException("Error in creating Ticket", HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<ResponseDto> deleteTicket(Long id) {
        try {
            if(ticketRepository.existsById(id)) {
                ticketRepository.deleteById(id);
                return ResponseEntity.ok(new ResponseDto("Deleted Ticket Successful"));
            }
            throw new TicketException("Ticked Not Found", HttpStatus.NOT_FOUND);
        } catch (TicketException e) {
            throw new TicketException(e.getMessage(), e.getStatus());
        }
    }

    public ResponseEntity<List<Ticket>> getTicketsCreatedByMe() {
        try {
            User user = SecurityUtil.getCurrentUser();
            List<Ticket> tickets = ticketRepository.getTicketsByCreatedBy(user);
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            throw new TicketException("Could not Fetch Tickets", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Ticket> getTicket(Long id) {
        if(!ticketRepository.existsById(id)) {
            throw new TicketException("Ticket Not Found", HttpStatus.NOT_FOUND);
        }
        Ticket ticket = ticketRepository.getById(id);
        return ResponseEntity.ok(ticket);
    }
}
