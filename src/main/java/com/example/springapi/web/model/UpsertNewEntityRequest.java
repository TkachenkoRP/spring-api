package com.example.springapi.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpsertNewEntityRequest {
    @NotBlank(message = "Текст новости должен быть заполнен!")
    private String content;
    @NotNull(message = "ID пользователя должно быть указано!")
    @Positive(message = "ID пользователя должно быть больше 0!")
    private Long userId;
    @NotNull(message = "ID категории должно быть указано!")
    @Positive(message = "ID категории должно быть больше 0!")
    private Long categoryId;
}
