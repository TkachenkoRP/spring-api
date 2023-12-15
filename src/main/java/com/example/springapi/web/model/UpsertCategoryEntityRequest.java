package com.example.springapi.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertCategoryEntityRequest {
    @NotBlank(message = "Название категории должно быть указано!")
    private String name;
}
