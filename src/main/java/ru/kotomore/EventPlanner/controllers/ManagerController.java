package ru.kotomore.EventPlanner.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kotomore.EventPlanner.dto.EventRequest;
import ru.kotomore.EventPlanner.models.Event;
import ru.kotomore.EventPlanner.models.User;
import ru.kotomore.EventPlanner.security.payload.response.MessageResponse;
import ru.kotomore.EventPlanner.security.services.UserDetailsImpl;
import ru.kotomore.EventPlanner.services.ContractService;
import ru.kotomore.EventPlanner.services.EventService;

@RestController
@RequestMapping("/api/manager/")
@PreAuthorize("hasRole('ROLE_MANAGER')")
@AllArgsConstructor
public class ManagerController {
    private final EventService eventService;
    private final ContractService contractService;

    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestBody EventRequest eventRequest,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.user();
        Event createdEvent = eventService.createEvent(user, eventRequest);
        return ResponseEntity.ok(new MessageResponse("Мероприятие успешно создано с ID: " + createdEvent.getId()));
    }

    @PostMapping("/contracts")
    public ResponseEntity<?> createContractRequest(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        contractService.createContractRequest(userDetails.user());
        return ResponseEntity.ok(new MessageResponse("Заявка успешно отправлена"));
    }
}
