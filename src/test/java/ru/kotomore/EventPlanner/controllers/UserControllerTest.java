package ru.kotomore.EventPlanner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.kotomore.EventPlanner.dto.RegistrationInfoRequest;
import ru.kotomore.EventPlanner.models.Event;
import ru.kotomore.EventPlanner.models.RegistrationInfo;
import ru.kotomore.EventPlanner.models.Role;
import ru.kotomore.EventPlanner.models.User;
import ru.kotomore.EventPlanner.services.EventService;

import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Mock
    private EventService eventService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        eventService = Mockito.mock(EventService.class);
    }

    @Test
    public void testCreateEventRequest_ShouldReturnOkWithMessage() throws Exception {
        UserController userController = new UserController(eventService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        when(eventService.registerUserForEvent(any(), any(), any(RegistrationInfoRequest.class))).thenReturn(getRegistrationInfo());

        mockMvc.perform(post("/api/user/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRegistrationInfoRequest())))
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

    private RegistrationInfoRequest getRegistrationInfoRequest() {
        RegistrationInfo registrationInfo = getRegistrationInfo();
        RegistrationInfoRequest registrationInfoRequest = new RegistrationInfoRequest();
        registrationInfoRequest.setFullName(registrationInfo.getFullName());
        registrationInfoRequest.setPcrTestResult(registrationInfo.isPcrTestResult());
        registrationInfoRequest.setAge(registrationInfo.getAge());
        return registrationInfoRequest;
    }
}
