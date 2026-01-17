package com.example.demo.dto.users;

import com.example.demo.entity.User;
import com.example.demo.entity.types.Role;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    Set<Role> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getUsername();
        this.roles = user.getRoles();
    }

    public UserDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
