package ru.kotomore.EventPlanner.services;

import ru.kotomore.EventPlanner.dto.LoginRequest;
import ru.kotomore.EventPlanner.dto.RegistrationUserRequest;
import ru.kotomore.EventPlanner.dto.UserInfoResponse;

public interface AuthService {
    /**
     * аутентификация пользователя
     *
     * @param loginRequest ДТО содержащий учетные данные (Логин и пароль)
     * @return Сообщение с информацией об авторизованном пользователе
     */
    UserInfoResponse login(LoginRequest loginRequest);

    /**
     * Регистрация пользователя
     *
     * @param registrationUserRequest ДТО содержащий информацию необходимую для регистрации пользователя
     */
    void registration(RegistrationUserRequest registrationUserRequest);
}
