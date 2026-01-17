package com.example.demo.service;

import com.example.demo.dto.projects.NewProjectDto;
import com.example.demo.dto.projects.ProjectDto;
import com.example.demo.dto.tickets.TicketDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectUser;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.exceptionHandlers.DeveloperException;
import com.example.demo.repository.ProjectUserRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeveloperService {

    private final ProjectRepository projectRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ProjectUserRepository projectUserRepository;

    public ResponseEntity<List<ProjectDto>> fetchProjects() {
        List<Project> projectList = projectRepository.findAll();
        List<ProjectDto> projects = projectList.stream().map(ProjectDto::new).toList();
        return ResponseEntity.ok(projects);
    }

    public ResponseEntity<List<TicketDto>> getMyTickets() {
        User user = SecurityUtil.getCurrentUser();
        List<Ticket> tickets = ticketRepository.findTicketsByUser(user.getId());
        List<TicketDto> ticketsDtos = tickets.stream()
                .map(TicketDto::new)
                .toList();

        return ResponseEntity.ok(ticketsDtos);
    }

    public ResponseEntity<List<TicketDto>> getProjectTickets(Long projectId) {
        try {
            if(!projectRepository.existsById(projectId)) {
                throw new DeveloperException("Project Doesnt Exist", HttpStatus.NOT_FOUND);
            }
            List<Ticket> ticketList = ticketRepository.findTicketsByProject(projectId);
            List<TicketDto> tickets = ticketList.stream().map(
                    TicketDto::new
            ).toList();

            return ResponseEntity.ok(tickets);

        } catch (DeveloperException e) {
            throw new DeveloperException("Could not Fetch the Project's Tickets", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ProjectDto> createProject(NewProjectDto dto) {
        try {
            Project project = new Project();
            project.setName(dto.name());

            ProjectUser projectUser = new ProjectUser();
            projectUser.setUser(SecurityUtil.getCurrentUser());
            project.addMember(projectUser);

            for(UserDto userDto : dto.users()) {
                Long userId = userDto.getId();
                User user = userRepository.getReferenceById(userId);

                projectUser = new ProjectUser();
                projectUser.setUser(user);

                project.addMember(projectUser);
            }

            Project savedProject = projectRepository.save(project);
            return ResponseEntity.ok(new ProjectDto(savedProject));

        } catch (DeveloperException e) {
            throw new DeveloperException("Failed to Create New Project", HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<List<ProjectDto>> fetchMyProjects() {
        try {
            Long userId = SecurityUtil.getCurrentUser().getId();
            List<Project> projects = projectUserRepository.findProjectsByUserId(userId);
            List<ProjectDto> finalProjects = projects.stream().map(ProjectDto::new).toList();

            return ResponseEntity.ok(finalProjects);
        } catch (DeveloperException e) {
            throw new DeveloperException("Could not Fetch All Projects", HttpStatus.NOT_FOUND);
        }
    }
}
