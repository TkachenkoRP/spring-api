package com.example.springapi.web.model;

import lombok.Data;

import java.time.Instant;

@Data
public class CommentEntityResponse {
    private Long id;
    private String text;
    private Instant createAt;
    private UserEntityResponse user;
    private Long fromNewId;
}
