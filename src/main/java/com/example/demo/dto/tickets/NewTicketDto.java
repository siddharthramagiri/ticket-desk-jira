package com.example.demo.dto.tickets;

import java.time.LocalDateTime;

public record NewTicketDto(
        String title,
        String description,
        String applicationName,
        String priority,
        LocalDateTime deadLine
) { }
