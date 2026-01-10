package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.projects.ProjectDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.Project;
import com.example.demo.entity.User;
import com.example.demo.entity.types.Role;
import com.example.demo.exceptionHandlers.UserException;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public ResponseEntity<ResponseDto> makeAdmin(Long id) {
        Optional<User> optionalUser = userRepository.findUserById(id);
        if(optionalUser.isEmpty()) {
            throw new UserException("User with Id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        user.getRoles().add(Role.ADMIN);
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseDto("User " + id + " is now Admin"));
    }

    public ResponseEntity<ResponseDto> assignRole(Long id, String role) {
        User user = userRepository.findUserById(id).orElse(null);
        if(user == null) {
            throw new UserException("User with Id : " + id + " Not existed", HttpStatus.NOT_FOUND);
        }
        user.getRoles().clear();

        try {
            Role userRole = Role.valueOf(role.toUpperCase());
            user.getRoles().add(userRole);
            userRepository.save(user);

            return ResponseEntity.ok(new ResponseDto("Successfully Updated Role"));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto("InValid Role Type"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Set<UserDto>> getAllUsers() {
        List<User> all_users = userRepository.findAll();

        Set<UserDto> users = all_users.stream().map(UserDto::new).collect(Collectors.toSet());
        UserDto me = new UserDto(SecurityUtil.getCurrentUser());
        users.remove(me);

        return ResponseEntity.ok(users);
    }

    public ResponseEntity<List<UserDto>> fetchUsersByRole(Role role) {
        List<User> userList = userRepository.findAllByRolesContains(role);
        List<UserDto> users = userList.stream().map(UserDto::new).toList();
//        for(UserDto user : users)
//            log.info(user.toString());
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<List<ProjectDto>> fetchProjects() {
        List<Project> projectList = projectRepository.findAll();
        List<ProjectDto> projects = projectList.stream().map(ProjectDto::new).toList();
        return ResponseEntity.ok(projects);
    }
}
