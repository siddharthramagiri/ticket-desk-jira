package com.example.demo.dto.projects;

import com.example.demo.entity.Project;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private List<String> usernames;

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.usernames = project.getMembers().stream().map(
                member -> member.getUser().getUsername()
        ).toList();
    }

    public ProjectDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
