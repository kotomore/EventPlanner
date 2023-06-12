package ru.kotomore.EventPlanner.services;

import ru.kotomore.EventPlanner.dto.EventRequest;
import ru.kotomore.EventPlanner.dto.RegistrationInfoRequest;
import ru.kotomore.EventPlanner.models.Event;
import ru.kotomore.EventPlanner.models.RegistrationInfo;
import ru.kotomore.EventPlanner.models.User;

public interface EventService {
    Event createEvent(User user, EventRequest eventRequest);

    RegistrationInfo registerUserForEvent(User user, Long eventId, RegistrationInfoRequest registrationInfoRequest);

}
