package ru.kotomore.EventPlanner.exceptions;

public class UserRoleNotExistException extends RuntimeException {
    public UserRoleNotExistException(String role) {
        super(String.format("Тип пользователя %s не существует. Возможные типы: user, manager", role));
    }
}
