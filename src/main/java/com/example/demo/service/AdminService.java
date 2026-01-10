package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.users.UserDto;
import com.example.demo.entity.User;
import com.example.demo.entity.types.Role;
import com.example.demo.exceptionHandlers.UserException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

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
}
