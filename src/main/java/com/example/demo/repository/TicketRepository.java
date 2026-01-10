package com.example.demo.repository;

import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> getTicketsByCreatedBy(User user);
}
