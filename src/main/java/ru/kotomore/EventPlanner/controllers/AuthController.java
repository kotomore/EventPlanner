package ru.kotomore.EventPlanner.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kotomore.EventPlanner.security.jwt.JwtUtils;
import ru.kotomore.EventPlanner.dto.LoginRequest;
import ru.kotomore.EventPlanner.dto.RegistrationUserRequest;
import ru.kotomore.EventPlanner.dto.MessageResponse;
import ru.kotomore.EventPlanner.dto.UserInfoResponse;
import ru.kotomore.EventPlanner.services.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private JwtUtils jwtUtils;
    private AuthService authService;


    @PostMapping("/login")
    @Operation(summary = "Получение API токена для аутентификации", description = "Пользователь предоставляет " +
            "учетные данные (логин и пароль) для получения токена доступа, " +
            "который используется для авторизации запросов")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        UserInfoResponse userInfoResponse = authService.login(loginRequest);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(userInfoResponse);
    }

    @PostMapping("/registration")
    @Operation(summary = "Регистрация нового пользователя", description = """
            Позволяет создать учетную запись для
            доступа к функционалу приложения. При регистрации необходимо предоставить данные пользователя,
            такие как логин, пароль и тип пользователя. После успешной регистрации пользователь
            получает доступ к своему аккаунту и может войти в систему
                        
                Возможные типы пользователей ("role")
                "user" - Обычный пользователь
                "manager" - Администратор мероприятия
            """)
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationUserRequest registrationUserRequest) {
        authService.registration(registrationUserRequest);

        return ResponseEntity.ok(new MessageResponse("Регистрация прошла успешно"));
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход из аккаунта")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Выход произведен успешно"));
    }
}