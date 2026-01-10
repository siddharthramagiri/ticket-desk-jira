package com.example.demo.dto.ticketDto;

import com.example.demo.entity.Ticket;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TicketDto {
        private Long id;
        private String title, description, applicationName;
        private String priority, status, createdBy;
        private LocalDateTime createdAt, updatedAt, deadLine;

        public TicketDto(Ticket ticket) {
            this.id = ticket.getId();
            this.title = ticket.getTitle();
            this.description = ticket.getDescription();
            this.applicationName = ticket.getApplicationName();
            this.priority = ticket.getPriority().toString();
            this.status = ticket.getStatus().toString();
            this.createdBy = ticket.getCreatedBy().getUsername();
            this.createdAt = ticket.getCreatedAt();
            this.updatedAt = ticket.getUpdatedAt();
            this.deadLine = ticket.getDeadLine();
        }
}
