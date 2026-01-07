package com.example.demo.dto.ticketDto;

import java.time.LocalDateTime;

public record NewTicketDto(
        String title,
        String description,
        String applicationName,
        String priority,
        LocalDateTime deadLine
) { }
