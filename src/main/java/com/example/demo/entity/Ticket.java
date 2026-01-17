package com.example.demo.entity;

import com.example.demo.entity.types.Priority;
import com.example.demo.entity.types.TicketStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    private String applicationName;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    @JsonIgnore
    private User createdBy; // customer

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deadLine;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TicketAssignee> assignees;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        status = TicketStatus.OPEN;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
