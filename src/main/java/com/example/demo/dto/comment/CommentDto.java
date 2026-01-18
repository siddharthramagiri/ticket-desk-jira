package com.example.demo.dto.comment;

import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.TicketComment;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDto {
    private Long id;
    private UserDto user;
    private String comment;
    private boolean aiGenerated;
    private LocalDateTime createdAt;

    public CommentDto(TicketComment comment) {
        this.id = comment.getId();
        this.user = new UserDto(comment.getUser());
        this.comment = comment.getComment();
        this.aiGenerated = comment.isAiGenerated();
        this.createdAt = comment.getCreatedAt();
    }
}
