package com.example.springapi.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentEntityListResponse {
    private List<CommentEntityResponse> comments = new ArrayList<>();
}
