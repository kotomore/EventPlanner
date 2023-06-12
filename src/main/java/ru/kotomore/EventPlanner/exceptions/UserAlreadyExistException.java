package ru.kotomore.EventPlanner.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String username) {
        super(String.format("Пользователь с username - %s уже существует", username));
    }
}
