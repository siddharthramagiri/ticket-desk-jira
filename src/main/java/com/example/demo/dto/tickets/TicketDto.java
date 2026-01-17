package com.example.demo.dto.tickets;

import com.example.demo.dto.projects.ProjectDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.Ticket;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class TicketDto {
        private Long id;
        private String title, description, applicationName;
        private String priority, status, createdBy;
        private LocalDateTime createdAt, updatedAt, deadLine;
        private Set<AssigneeDto> assignees;

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

            this.assignees = ticket.getAssignees()
                    .stream()
                    .map(AssigneeDto::new)
                    .collect(Collectors.toSet());
        }
}
