package ru.kotomore.EventPlanner.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import ru.kotomore.EventPlanner.dto.EventRequest;
import ru.kotomore.EventPlanner.dto.RegistrationInfoRequest;
import ru.kotomore.EventPlanner.exceptions.ContractNotAcceptedException;
import ru.kotomore.EventPlanner.exceptions.EventNotFoundException;
import ru.kotomore.EventPlanner.models.*;
import ru.kotomore.EventPlanner.repositories.ContractRepository;
import ru.kotomore.EventPlanner.repositories.EventRepository;
import ru.kotomore.EventPlanner.repositories.RegistrationInfoRepository;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private RegistrationInfoRepository registrationInfoRepository;

    private final ModelMapper modelMapper = new ModelMapper();


    @BeforeEach
    public void setUp() {
        eventRepository = Mockito.mock(EventRepository.class);
        contractRepository = Mockito.mock(ContractRepository.class);
        registrationInfoRepository = Mockito.mock(RegistrationInfoRepository.class);
    }

    @Test
    public void testCreateEvent_WithAcceptedContract_ShouldCreateEvent() {
        EventServiceImpl eventService = new EventServiceImpl(eventRepository, contractRepository,
                registrationInfoRepository, modelMapper);

        User user = getUser();
        EventRequest eventRequest = getEventRequest();
        Contract contract = getContract();
        contract.setContractStatus(ContractStatus.ACCEPTED);
        Event savedEvent = getEvent();

        when(contractRepository.findByUser(user)).thenReturn(Optional.of(contract));
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        Event result = eventService.createEvent(user, eventRequest);

        assertNotNull(result);
        assertEquals(savedEvent, result);

        verify(contractRepository).findByUser(user);
        verify(eventRepository, Mockito.times(1)).save(Mockito.any(Event.class));
    }

    @Test
    public void testCreateEvent_WithNonAcceptedContract_ShouldThrowContractNotAcceptedException() {
        EventServiceImpl eventService = new EventServiceImpl(eventRepository, contractRepository,
                registrationInfoRepository, modelMapper);

        User user = getUser();
        EventRequest eventRequest = getEventRequest();
        Contract contract = getContract();
        contract.setContractStatus(ContractStatus.PENDING);

        when(contractRepository.findByUser(user)).thenReturn(Optional.of(contract));

        assertThrows(ContractNotAcceptedException.class, () -> eventService.createEvent(user, eventRequest));

        verify(contractRepository).findByUser(user);
        verify(eventRepository, never()).save(any());
    }

    @Test
    public void testRegisterUserForEvent_WithExistingEvent_ShouldRegisterUser() {
        EventServiceImpl eventService = new EventServiceImpl(eventRepository, contractRepository,
                registrationInfoRepository, modelMapper);

        User user = getUser();
        RegistrationInfo registrationInfo = getRegistrationInfo();

        RegistrationInfoRequest registrationInfoRequest = new RegistrationInfoRequest();
        registrationInfoRequest.setAge(registrationInfo.getAge());
        registrationInfoRequest.setPcrTestResult(registrationInfo.isPcrTestResult());
        registrationInfoRequest.setFullName(registrationInfo.getFullName());

        Event event = getEvent();

        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
        when(registrationInfoRepository.save(any(RegistrationInfo.class))).thenReturn(registrationInfo);

        RegistrationInfo result = eventService.registerUserForEvent(user, event.getId(), registrationInfoRequest);

        assertNotNull(result);
        assertEquals(registrationInfo, result);

        verify(eventRepository).findById(event.getId());
        verify(registrationInfoRepository, Mockito.times(1)).save(Mockito.any(RegistrationInfo.class));
    }

    @Test
    public void testRegisterUserForEvent_WithNonExistingEvent_ShouldThrowEventNotFoundException() {
        EventServiceImpl eventService = new EventServiceImpl(eventRepository, contractRepository,
                registrationInfoRepository, modelMapper);

        User user = getUser();
        Long eventId = 1L;
        RegistrationInfoRequest registrationInfoRequest = new RegistrationInfoRequest();

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.registerUserForEvent(user, eventId, registrationInfoRequest));

        verify(eventRepository).findById(eventId);
        verify(registrationInfoRepository, never()).save(any());
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("John");
        user.setPassword("Password");
        user.setRole(Role.ROLE_USER);
        return user;
    }

    private Contract getContract() {
        Contract contract = new Contract();
        contract.setUser(getUser());
        contract.setContractStatus(ContractStatus.ACCEPTED);
        contract.setId(1L);
        return contract;
    }

    private Event getEvent() {
        Event event = new Event();
        event.setUser(getUser());
        event.setId(1L);
        event.setCost(123);
        event.setDate(new Date());
        event.setName("Some event name");
        return event;
    }

    private EventRequest getEventRequest() {
        Event event = getEvent();
        EventRequest eventRequest = new EventRequest();
        eventRequest.setCost(event.getCost());
        eventRequest.setDate(event.getDate());
        eventRequest.setName(event.getName());
        return eventRequest;
    }

    private RegistrationInfo getRegistrationInfo() {
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setUser(getUser());
        registrationInfo.setEvent(getEvent());
        registrationInfo.setPaid(false);
        registrationInfo.setId(1L);
        registrationInfo.setAge(18);
        registrationInfo.setFullName("John Doe");
        registrationInfo.setPcrTestResult(false);
        return registrationInfo;
    }
}
