package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "project_users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "user_id"}))
public class ProjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

