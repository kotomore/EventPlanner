package ru.kotomore.EventPlanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventRequest {

    @Schema(description = "Название мероприятия")
    @NotNull(message = "Укажите название мероприятия")
    private String name;

    @Schema(description = "Стоимость участия в мероприятии")
    @NotNull(message = "Укажите стоимость участия в мероприятии")
    private double cost;

    @Schema(description = "Дата мероприятия")
    @NotNull(message = "Укажите дату мероприятия")
    private Date date;
}
