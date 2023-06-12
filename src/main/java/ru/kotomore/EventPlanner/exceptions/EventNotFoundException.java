package ru.kotomore.EventPlanner.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(Long id) {
        super(String.format("Мероприятие с ID - %d не найдено", id));
    }
}
