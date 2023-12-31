package ru.kotomore.EventPlanner.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kotomore.EventPlanner.dto.EventRequest;
import ru.kotomore.EventPlanner.dto.RegistrationInfoRequest;
import ru.kotomore.EventPlanner.exceptions.ContractNotAcceptedException;
import ru.kotomore.EventPlanner.exceptions.ContractNotFoundException;
import ru.kotomore.EventPlanner.exceptions.EventNotFoundException;
import ru.kotomore.EventPlanner.models.*;
import ru.kotomore.EventPlanner.repositories.ContractRepository;
import ru.kotomore.EventPlanner.repositories.EventRepository;
import ru.kotomore.EventPlanner.repositories.RegistrationInfoRepository;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ContractRepository contractRepository;
    private final RegistrationInfoRepository registrationInfoRepository;
    private final ModelMapper modelMapper;

    @Override
    public Event createEvent(User user, EventRequest eventRequest) {

        Contract contract = contractRepository.findByUser(user).orElseThrow(ContractNotFoundException::new);
        if (contract.getContractStatus() != ContractStatus.ACCEPTED) {
            throw new ContractNotAcceptedException();
        }

        Event savedEvent = modelMapper.map(eventRequest, Event.class);
        savedEvent.setUser(user);
        return eventRepository.save(savedEvent);
    }

    @Override
    public RegistrationInfo registerUserForEvent(User user, Long eventId, RegistrationInfoRequest registrationInfoRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        RegistrationInfo registrationInfo = modelMapper.map(registrationInfoRequest, RegistrationInfo.class);
        registrationInfo.setUser(user);
        registrationInfo.setEvent(event);
        registrationInfo.setPaid(false);

        return registrationInfoRepository.save(registrationInfo);
    }
}
