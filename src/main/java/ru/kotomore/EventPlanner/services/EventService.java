package ru.kotomore.EventPlanner.services;

import ru.kotomore.EventPlanner.dto.EventRequest;
import ru.kotomore.EventPlanner.dto.RegistrationInfoRequest;
import ru.kotomore.EventPlanner.models.Event;
import ru.kotomore.EventPlanner.models.RegistrationInfo;
import ru.kotomore.EventPlanner.models.User;

public interface EventService {
    /**
     * Создать мероприятие
     *
     * @param user          Пользователь, который создает мероприятие
     * @param eventRequest  ДТО с подробной информацией о мероприятии
     * @return Информацию о мероприятии
     */
    Event createEvent(User user, EventRequest eventRequest);

    /**
     * Отправить заявку на участие в мероприятии
     *
     * @param user          Пользователь, который отправляет заявку
     * @param eventId       ID мероприятия для участия
     * @param registrationInfoRequest  ДТО с подробной информацией о пользователе
     * @return информацию о заявке
     */
    RegistrationInfo registerUserForEvent(User user, Long eventId, RegistrationInfoRequest registrationInfoRequest);
}
