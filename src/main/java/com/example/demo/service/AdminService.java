package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.entity.User;
import com.example.demo.entity.types.Role;
import com.example.demo.exceptionHandlers.UserException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

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

        switch (role.toUpperCase()) {
            case "ADMIN":
                user.getRoles().add(Role.ADMIN);
                break;
            case "DEVELOPER":
                user.getRoles().add(Role.DEVELOPER);
                break;
            case "CLIENT":
                user.getRoles().add(Role.CLIENT);
                break;
            case "SUPPORT":
                user.getRoles().add(Role.SUPPORT);
                break;
            default:
                return new ResponseEntity<>(new ResponseDto("InValid Role Type"), HttpStatus.NOT_FOUND);
        }
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseDto("Successfully Updated Role"));
    }
}
