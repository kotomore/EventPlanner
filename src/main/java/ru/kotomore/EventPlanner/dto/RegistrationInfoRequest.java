package ru.kotomore.EventPlanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationInfoRequest {

    @NotNull(message = "Укажите Ф.И.О")
    @Schema(description = "Ф.И.О")
    private String fullName;

    @Schema(description = "Возраст")
    @NotNull(message = "Укажите ваш возраст")
    private Integer age;

    @Schema(description = "Результат ПЦР теста")
    @NotNull(message = "Укажите результат ПЦР теста")
    private Boolean pcrTestResult;
}
