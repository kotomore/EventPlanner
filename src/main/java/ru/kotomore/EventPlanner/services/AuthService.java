package ru.kotomore.EventPlanner.services;

import ru.kotomore.EventPlanner.dto.LoginRequest;
import ru.kotomore.EventPlanner.dto.RegistrationUserRequest;
import ru.kotomore.EventPlanner.dto.UserInfoResponse;

public interface AuthService {
    UserInfoResponse login(LoginRequest loginRequest);

    void registration(RegistrationUserRequest registrationUserRequest);
}
