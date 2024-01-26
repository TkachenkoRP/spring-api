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
    @NotBlank(message = "Имя пользователя должно быть указано!", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    @Size(min = 3, max = 20, message = "Имя пользователя не может быть меньше {min} и больше {max}!", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private String name;
    @NotBlank(message = "Пароль должен быть укзан!", groups = {CreateValidationGroup.class})
    @Size(min = 3, message = "Пароль должен содержать минимум {min} символов!", groups = {CreateValidationGroup.class})
    private String password;

    public interface CreateValidationGroup {}

    public interface UpdateValidationGroup {}
}
