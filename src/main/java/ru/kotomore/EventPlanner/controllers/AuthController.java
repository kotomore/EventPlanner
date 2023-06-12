package ru.kotomore.EventPlanner.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kotomore.EventPlanner.security.jwt.JwtUtils;
import ru.kotomore.EventPlanner.security.payload.request.LoginRequest;
import ru.kotomore.EventPlanner.security.payload.request.RegistrationRequest;
import ru.kotomore.EventPlanner.security.payload.response.MessageResponse;
import ru.kotomore.EventPlanner.security.payload.response.UserInfoResponse;
import ru.kotomore.EventPlanner.services.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private JwtUtils jwtUtils;
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        UserInfoResponse userInfoResponse = authService.login(loginRequest);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(userInfoResponse);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        authService.registration(registrationRequest);

        return ResponseEntity.ok(new MessageResponse("Регистрация прошла успешно"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Выход произведен успешно"));
    }
}