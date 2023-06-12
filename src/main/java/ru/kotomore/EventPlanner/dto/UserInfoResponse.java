package ru.kotomore.EventPlanner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private String role;
}
