package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.app.AppDto;
import com.example.demo.dto.tickets.NewTicketDto;
import com.example.demo.dto.tickets.TicketDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.entity.types.Priority;
import com.example.demo.exceptionHandlers.TicketException;
import com.example.demo.exceptionHandlers.UserException;
import com.example.demo.repository.ApplicationRepository;
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


@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final TicketRepository ticketRepository;
    private final ApplicationRepository applicationRepository;

    public ResponseEntity<ResponseDto> addTicket(NewTicketDto ticketDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new UserException("Authentication Failed", HttpStatus.BAD_REQUEST);
        }

        Application app = applicationRepository.getReferenceById(ticketDto.applicationId());
        try {
            User user = (User) authentication.getPrincipal();

            Ticket ticket = Ticket.builder()
                    .title(ticketDto.title())
                    .application(app)
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

    public ResponseEntity<List<TicketDto>> getTicketsCreatedByMe() {
        try {
            User user = SecurityUtil.getCurrentUser();
            List<Ticket> tickets = ticketRepository.getTicketsByCreatedBy(user);

            List<TicketDto> res = tickets.stream().map(TicketDto::new).toList();
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new TicketException("Could not Fetch Tickets", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Ticket> getTicket(Long id) {
        if(!ticketRepository.existsById(id)) {
            throw new TicketException("Ticket Not Found", HttpStatus.NOT_FOUND);
        }
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return ResponseEntity.ok(ticket);
    }

    public ResponseEntity<List<AppDto>> getMyApps() {
        User user = SecurityUtil.getCurrentUser();
        List<Application> apps = user.getApplications();

        List<AppDto> applications = apps.stream()
                .map(AppDto::new)
                .toList();

        return ResponseEntity.ok(applications);
    }


    public ResponseEntity<ResponseDto> addApplication(Long id) {
        User user = SecurityUtil.getCurrentUser();

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.getUsers().add(user);
        applicationRepository.save(app);

        return ResponseEntity.ok(new ResponseDto("Added Application"));
    }

}
