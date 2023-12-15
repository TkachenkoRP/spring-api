package com.example.springapi.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityResponse {
    private Long id;
    private String name;
    private Integer countNew;
    private Integer countComment;
}
