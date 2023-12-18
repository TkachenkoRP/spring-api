package com.example.springapi.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserEntityRequest {
    @NotBlank(message = "Имя пользователя должно быть указано!")
    @Size(min = 3, max = 20, message = "Имя пользователя не может быть меньше {min} и больше {max}!")
    private String name;
}
