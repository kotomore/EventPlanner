package ru.kotomore.EventPlanner.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kotomore.EventPlanner.dto.RegistrationInfoRequest;
import ru.kotomore.EventPlanner.models.RegistrationInfo;
import ru.kotomore.EventPlanner.models.User;
import ru.kotomore.EventPlanner.security.payload.response.MessageResponse;
import ru.kotomore.EventPlanner.security.services.UserDetailsImpl;
import ru.kotomore.EventPlanner.services.EventService;

@RestController
@RequestMapping("/api/user/")
@PreAuthorize("hasRole('ROLE_USER')")
@AllArgsConstructor
public class UserController {
    private final EventService eventService;

    @PostMapping("/events/{id}")
    public ResponseEntity<?> createEventRequest(@PathVariable Long id,
                                                @RequestBody RegistrationInfoRequest registrationInfoRequest,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.user();
        RegistrationInfo createdEvent = eventService.registerUserForEvent(user, id, registrationInfoRequest);
        return ResponseEntity.ok(new MessageResponse("Создана заявка на участие в мероприятии с ID " + createdEvent.getId()));
    }
}
