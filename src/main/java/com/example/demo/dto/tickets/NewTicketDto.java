package com.example.demo.dto.tickets;

import java.time.LocalDateTime;

public record NewTicketDto(
        String title,
        String description,
        Long applicationId,
        String priority,
        LocalDateTime deadLine
) { }
