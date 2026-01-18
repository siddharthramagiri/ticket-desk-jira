package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.comment.CommentDto;
import com.example.demo.dto.comment.NewCommentDto;
import com.example.demo.dto.projects.NewProjectDto;
import com.example.demo.dto.projects.ProjectDto;
import com.example.demo.dto.tickets.TicketDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.types.Role;
import com.example.demo.service.AdminService;
import com.example.demo.service.DeveloperService;
import com.example.demo.service.PrivateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/private")
@RequiredArgsConstructor
public class PrivateController {

    private final AdminService adminService;
    private final DeveloperService developerService;
    private final PrivateService privateService;

    @GetMapping
    public ResponseEntity<ResponseDto> privateRoute() {
        return ResponseEntity.ok(new ResponseDto("Private Route Accessed by Login"));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> fetchUsersByRole(@RequestParam String role) {
        return adminService.fetchUsersByRole(Role.valueOf(role));
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDto>> fetchProjects() {
        return developerService.fetchProjects();
    }

    @GetMapping("/myProjects")
    public ResponseEntity<List<ProjectDto>> fetchMyProjects() {
        return developerService.fetchMyProjects();
    }

    @PostMapping("/create-project")
    public ResponseEntity<ProjectDto> createProject(@RequestBody NewProjectDto newProject) {
        return developerService.createProject(newProject);
    }


    @GetMapping("/get-my-tickets")
    public ResponseEntity<List<TicketDto>> getMyTickets() {
        return developerService.getMyTickets();
    }

    @GetMapping("/get-project-tickets/{projectId}")
    public ResponseEntity<List<TicketDto>> getProjectTickets(@PathVariable Long projectId) {
        return developerService.getProjectTickets(projectId);
    }

    @PostMapping("/comment/{ticketId}")
    public ResponseEntity<ResponseDto> createComment(@PathVariable Long ticketId, @RequestBody NewCommentDto dto) {
        return privateService.addCommentToTicket(ticketId, dto.comment(), dto.aiGenerated());
    }

    @GetMapping("/comments/{ticketId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long ticketId) {
        return privateService.getTicketComments(ticketId);
    }
}
