package ru.kotomore.EventPlanner.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventRequest {
    private String name;
    private double cost;
    private Date date;
}
