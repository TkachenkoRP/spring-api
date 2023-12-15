package com.example.springapi.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntityResponse {
    private Long id;
    private String name;
    private Integer countNews;
}
