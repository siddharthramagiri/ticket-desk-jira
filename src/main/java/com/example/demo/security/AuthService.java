package com.example.demo.security;


import com.example.demo.dto.authDto.LoginResponseDto;
import com.example.demo.dto.authDto.SignUpResponseDto;
import com.example.demo.entity.User;
import com.example.demo.entity.types.Role;
import com.example.demo.exceptionHandlers.UserException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    public ResponseEntity<SignUpResponseDto> signup(String username, String password) {
        User user = signUpInternal(username, password);
        return ResponseEntity.ok(new SignUpResponseDto(
                user.getId(), user.getUsername(), "User Signed Up Successfully")
        );
    }
    public User signUpInternal(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null) {
            throw new UserException("User with email " + username + " Already Exists", HttpStatus.CONFLICT);
        }
        user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(Set.of(Role.CLIENT))
                .build();

        return userRepository.save(user);
    }

    public ResponseEntity<LoginResponseDto> login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
            User user = (User) authentication.getPrincipal();
            String token = authUtil.generateToken(user);

            return ResponseEntity.ok(
                    LoginResponseDto.builder()
                            .token(token)
                            .id(user.getId())
                            .email(user.getUsername())
                            .roles(user.getRoles())
                            .build()
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }
    }
}
