package ru.kotomore.EventPlanner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationInfoRequest {

    private String fullName;

    private int age;

    private boolean pcrTestResult;
}
