package ru.kotomore.EventPlanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationUserRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "Имя пользователя")
    private String username;

    @NotBlank
    @Schema(description = "Тип пользователя (user - обычный пользователь, manager - администратор мероприятия)")
    private String role;

    @NotBlank
    @Size(min = 6, max = 20)
    @Schema(description = "Пароль")
    private String password;
}