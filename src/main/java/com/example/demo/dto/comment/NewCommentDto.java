package com.example.demo.dto.comment;

public record NewCommentDto (
        String comment,
        boolean aiGenerated
) {}
