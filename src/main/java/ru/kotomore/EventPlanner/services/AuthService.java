package ru.kotomore.EventPlanner.services;

import ru.kotomore.EventPlanner.security.payload.request.LoginRequest;
import ru.kotomore.EventPlanner.security.payload.request.RegistrationRequest;
import ru.kotomore.EventPlanner.security.payload.response.UserInfoResponse;

public interface AuthService {
    UserInfoResponse login(LoginRequest loginRequest);

    void registration(RegistrationRequest registrationRequest);
}
