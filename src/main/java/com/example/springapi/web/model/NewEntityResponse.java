package com.example.springapi.web.model;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class NewEntityResponse {
    private Long id;
    private Instant createAt;
    private CategoryEntityResponse category;
    private UserEntityResponse user;
    private String content;
    private List<CommentEntityResponse> comments = new ArrayList<>();
}
