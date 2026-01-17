package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProjectUser> members = new ArrayList<>();

    public void addMember(ProjectUser projectUser) {
        members.add(projectUser);
        projectUser.setProject(this);
    }
}

