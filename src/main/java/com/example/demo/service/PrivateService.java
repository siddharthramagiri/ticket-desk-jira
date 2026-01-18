package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.comment.CommentDto;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.TicketComment;
import com.example.demo.entity.User;
import com.example.demo.exceptionHandlers.DeveloperException;
import com.example.demo.repository.TicketCommentRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateService {


    private final TicketRepository ticketRepository;
    private final TicketCommentRepository ticketCommentRepository;

    public ResponseEntity<ResponseDto> addCommentToTicket(Long ticketId, String comment, boolean aiGenerated) {
        try {
            User currentUser = SecurityUtil.getCurrentUser();
            Ticket ticket = ticketRepository.getReferenceById(ticketId);

            TicketComment newComment = TicketComment.builder()
                    .ticket(ticket)
                    .comment(comment)
                    .user(currentUser)
                    .aiGenerated(aiGenerated)
                    .build();

            ticketCommentRepository.save(newComment);

            return ResponseEntity.ok(new ResponseDto("Created Ticket Successfully"));
        } catch (DeveloperException e) {
            throw new DeveloperException("Comment Failed", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<CommentDto>> getTicketComments(Long ticketId) {
        try {
            List<TicketComment> commentList = ticketCommentRepository.findByTicket_Id(ticketId);
            List<CommentDto> comments = commentList.stream().map(
                    CommentDto::new
            ).toList();

            return ResponseEntity.ok(comments);
        } catch (DeveloperException e) {
            throw new DeveloperException("Failed to get Comments", HttpStatus.NOT_FOUND);
        }
    }
}
