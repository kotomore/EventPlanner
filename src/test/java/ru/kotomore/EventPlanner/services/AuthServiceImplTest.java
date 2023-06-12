package ru.kotomore.EventPlanner.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kotomore.EventPlanner.dto.RegistrationUserRequest;
import ru.kotomore.EventPlanner.exceptions.UserAlreadyExistException;
import ru.kotomore.EventPlanner.exceptions.UserRoleNotExistException;
import ru.kotomore.EventPlanner.models.Role;
import ru.kotomore.EventPlanner.models.User;
import ru.kotomore.EventPlanner.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);

    }

    @Test
    public void testRegistration_WithValidRequest_ShouldSaveUser() {
        AuthServiceImpl authService = new AuthServiceImpl(authenticationManager, userRepository, encoder);

        RegistrationUserRequest registrationUserRequest = getRegistrationUserRequest();
        String username = registrationUserRequest.getUsername();

        User expectedUser = new User(username, encoder.encode(registrationUserRequest.getPassword()));
        expectedUser.setRole(Role.ROLE_USER);

        when(userRepository.existsByUsername(username)).thenReturn(false);

        authService.registration(registrationUserRequest);

        verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void testRegistration_WithExistingUsername_ShouldThrowUserAlreadyExistException() {
        AuthServiceImpl authService = new AuthServiceImpl(authenticationManager, userRepository, encoder);

        RegistrationUserRequest registrationUserRequest = getRegistrationUserRequest();

        when(userRepository.existsByUsername(registrationUserRequest.getUsername())).thenReturn(true);

        assertThrows(UserAlreadyExistException.class, () -> authService.registration(registrationUserRequest));
    }

    @Test
    public void testRegistration_WithInvalidRole_ShouldThrowUserRoleNotExistException() {
        AuthServiceImpl authService = new AuthServiceImpl(authenticationManager, userRepository, encoder);

        RegistrationUserRequest registrationUserRequest = getRegistrationUserRequest();
        registrationUserRequest.setRole("invalidRole");

        when(userRepository.existsByUsername(registrationUserRequest.getUsername())).thenReturn(false);

        assertThrows(UserRoleNotExistException.class, () -> authService.registration(registrationUserRequest));
    }

    private static RegistrationUserRequest getRegistrationUserRequest() {
        String username = "testuser";
        String password = "testpassword";
        String role = "user";

        RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest();
        registrationUserRequest.setUsername(username);
        registrationUserRequest.setPassword(password);
        registrationUserRequest.setRole(role);
        return registrationUserRequest;
    }
}