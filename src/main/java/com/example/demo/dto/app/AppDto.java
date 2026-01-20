package com.example.demo.dto.app;

import com.example.demo.entity.Application;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AppDto {
    private Long id;
    private String name;

    public AppDto(Application app) {
        this.id = app.getId();
        this.name = app.getName();
    }
}
