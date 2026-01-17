package com.example.demo.repository;

import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> getTicketsByCreatedBy(User user);

    @Query("""
    SELECT ta.ticket
    FROM TicketAssignee ta
    WHERE ta.user.id = :userId
""")
    List<Ticket> findTicketsByUser(Long userId);

    @Query("""
    SELECT ta.ticket
    FROM TicketAssignee ta
    WHERE ta.project.id = :projectId
""")
    List<Ticket> findTicketsByProject(Long projectId);

}
