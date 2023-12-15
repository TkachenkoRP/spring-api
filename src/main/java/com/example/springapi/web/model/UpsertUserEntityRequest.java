package com.example.springapi.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpsertUserEntityRequest {
    @NotBlank(message = "Имя пользователя должно быть указано!")
    @Size(min = 3, max = 20, message = "Имя пользователя не может быть меньше {min} и больше {max}!")
    private String name;
}
