package com.example.springapi.web.model;

import lombok.Data;

import java.time.Instant;

@Data
public class NewEntityForListResponse {
    private Long id;
    private Instant createAt;
    private CategoryEntityResponse category;
    private UserEntityResponse user;
    private String content;
    private Integer countComments;
}
