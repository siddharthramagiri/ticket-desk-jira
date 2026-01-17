package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.tickets.TicketDto;
import com.example.demo.entity.Project;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.TicketAssignee;
import com.example.demo.entity.User;
import com.example.demo.entity.types.TicketStatus;
import com.example.demo.exceptionHandlers.TicketException;
import com.example.demo.exceptionHandlers.UserException;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TicketAssigneeRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupportService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TicketAssigneeRepository ticketAssigneeRepository;

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

    public ResponseEntity<ResponseDto> updateStatus(Long id, TicketStatus newStatus) {
        try {
            if(!ticketRepository.existsById(id)) {
                throw new TicketException("Tcket Not found", HttpStatus.NOT_FOUND);
            }
            Ticket ticket = ticketRepository.getById(id);
            ticket.setStatus(newStatus);
            ticketRepository.save(ticket);
            return ResponseEntity.ok(new ResponseDto("Status Updated Successfully"));
        } catch (Exception e) {
            throw new TicketException("Error: Failed to update Ticket", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto> assignTicket(Long ticketId, Long id, boolean flag) {
        try {
            if(!ticketRepository.existsById(ticketId)) {
                throw new TicketException("Tcket Not found", HttpStatus.NOT_FOUND);
            }
            User loggedUser = SecurityUtil.getCurrentUser();
            Ticket ticket = ticketRepository.getById(ticketId);

            TicketAssignee assignee = TicketAssignee.builder()
                    .ticket(ticket)
                    .assignedBy(loggedUser)
                    .build();

            if(flag) {
                Optional<User> user = userRepository.findById(id);
                if(user.isEmpty()) throw new UserException("User Not found", HttpStatus.NOT_FOUND);
                assignee.setUser(user.get());
            } else {
                Optional<Project> project = projectRepository.findById(id);
                if(project.isEmpty()) throw new TicketException("Project Not Found", HttpStatus.NOT_FOUND);
                assignee.setProject(project.get());
            }
            ticket.getAssignees().add(assignee);

            ticketRepository.save(ticket);

            return ResponseEntity.ok(new ResponseDto("Status Updated Successfully"));
        } catch (Exception e) {
            throw new TicketException("Error: Failed to update Ticket", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto> removeAssignee(Long ticketId, Long id) {
        try {
            if(!ticketAssigneeRepository.existsById(id) || !ticketRepository.existsById(ticketId)) {
                throw new TicketException("Ticket or User doesn't exists", HttpStatus.NOT_FOUND);
            }
            TicketAssignee assignee = ticketAssigneeRepository.findById(id).get();
            Ticket ticket = ticketRepository.findById(ticketId).get();
            ticket.getAssignees().remove(assignee);
            ticketRepository.save(ticket);

            return ResponseEntity.ok(new ResponseDto("Removed Assignee"));

        } catch (Exception e) {
            throw new TicketException("Could not remove Assignee", HttpStatus.BAD_GATEWAY);
        }
    }
}
