package com.example.demo.repository;

import com.example.demo.entity.Ticket;
import com.example.demo.entity.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, Long> {

    List<TicketComment> ticket(Ticket ticket);

    List<TicketComment> findByTicket_Id(Long ticketId);
}
