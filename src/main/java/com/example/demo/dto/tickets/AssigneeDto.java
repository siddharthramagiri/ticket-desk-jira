package com.example.demo.dto.tickets;

import com.example.demo.dto.projects.ProjectDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.TicketAssignee;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AssigneeDto {
    private Long id;
    private UserDto user;
    private ProjectDto project;
//    private Long userId;
//    private String userEmail;
//    private Long projectId;
//    private String projectName;
    private String assignedBy;
    private LocalDateTime assignedAt;

    public AssigneeDto(TicketAssignee assignee) {
        this.id = assignee.getId();
        this.assignedAt = assignee.getAssignedAt();
        this.assignedBy = assignee.getAssignedBy().getUsername();

        if (assignee.getUser() != null) {
//            this.userId = assignee.getUser().getId();
//            this.userEmail = assignee.getUser().getUsername();
            this.user = new UserDto(assignee.getUser().getId(), assignee.getUser().getUsername());
        }

        if (assignee.getProject() != null) {
//            this.projectId = assignee.getProject().getId();
//            this.projectName = assignee.getProject().getName();
            this.project = new ProjectDto(assignee.getProject().getId(), assignee.getProject().getName());
        }
    }
}