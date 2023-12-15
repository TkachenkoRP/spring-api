package com.example.springapi.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewEntityFilter {
    private Integer pageSize;
    private Integer pageNumber;
    private Long userId;
    private Long categoryId;
}
