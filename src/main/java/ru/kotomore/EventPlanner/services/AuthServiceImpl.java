package ru.kotomore.EventPlanner.services;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kotomore.EventPlanner.models.Role;
import ru.kotomore.EventPlanner.models.User;
import ru.kotomore.EventPlanner.repositories.UserRepository;
import ru.kotomore.EventPlanner.security.payload.request.LoginRequest;
import ru.kotomore.EventPlanner.security.payload.request.RegistrationRequest;
import ru.kotomore.EventPlanner.security.payload.response.UserInfoResponse;
import ru.kotomore.EventPlanner.security.services.UserDetailsImpl;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Override
    public UserInfoResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        return new UserInfoResponse(userDetails.user().getId(), userDetails.getUsername(), role);
    }

    @Override
    public void registration(RegistrationRequest registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new RuntimeException("Error: Имя пользователя уже существует");
        }

        User user = new User(registrationRequest.getUsername(),
                encoder.encode(registrationRequest.getPassword()));

        switch (registrationRequest.getRole().toLowerCase()) {
            case "user" -> user.setRole(Role.ROLE_USER);
            case "manager" -> user.setRole(Role.ROLE_MANAGER);
            default -> throw new RuntimeException("Error: Тип пользователя не существует");
        }

        userRepository.save(user);
    }
}
