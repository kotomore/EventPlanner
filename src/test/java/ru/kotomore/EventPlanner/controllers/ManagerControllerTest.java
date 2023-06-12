package ru.kotomore.EventPlanner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.kotomore.EventPlanner.dto.EventRequest;
import ru.kotomore.EventPlanner.models.Event;
import ru.kotomore.EventPlanner.models.Role;
import ru.kotomore.EventPlanner.models.User;
import ru.kotomore.EventPlanner.services.ContractService;
import ru.kotomore.EventPlanner.services.EventService;

import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ManagerControllerTest {

    @Mock
    private EventService eventService;

    @Mock
    private ContractService contractService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        eventService = Mockito.mock(EventService.class);
        contractService = Mockito.mock(ContractService.class);
    }

    @Test
    public void testCreateEvent_ShouldReturnOkWithMessage() throws Exception {
        ManagerController managerController = new ManagerController(eventService, contractService);
        mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
        EventRequest eventRequest = getEventRequest();

        when(eventService.createEvent(any(), any(EventRequest.class))).thenReturn(getEvent());

        mockMvc.perform(post("/api/manager/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testCreateContractRequest_ShouldReturnOkWithMessage() throws Exception {
        ManagerController managerController = new ManagerController(eventService, contractService);
        mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();

        mockMvc.perform(post("/api/manager/contracts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("John");
        user.setPassword("Password");
        user.setRole(Role.ROLE_USER);
        return user;
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
}
