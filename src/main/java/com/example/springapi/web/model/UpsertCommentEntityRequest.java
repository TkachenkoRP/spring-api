package com.example.springapi.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpsertCommentEntityRequest {
    @NotBlank(message = "Текст комментария должен быть заполнен!")
    private String text;
    @NotNull(message = "ID новости должно быть указано!")
    @Positive(message = "ID новости должно быть больше 0!")
    private Long newId;
}
