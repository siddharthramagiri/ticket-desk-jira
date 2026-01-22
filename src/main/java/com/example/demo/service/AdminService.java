package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.app.AppDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.Application;
import com.example.demo.entity.User;
import com.example.demo.entity.types.Role;
import com.example.demo.exceptionHandlers.UserException;
import com.example.demo.repository.ApplicationRepository;
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
    private final ApplicationRepository applicationRepository;

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
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<ResponseDto> createApp(String appName) {
        Application application = Application.builder().name(appName).build();
        applicationRepository.save(application);
        return ResponseEntity.ok(new ResponseDto("Created Application Successfully"));
    }

    public ResponseEntity<List<AppDto>> getAllApps() {
        List<Application> apps = applicationRepository.findAll();
        List<AppDto> applications = apps.stream().map(AppDto::new).toList();

        return ResponseEntity.ok(applications);
    }

}