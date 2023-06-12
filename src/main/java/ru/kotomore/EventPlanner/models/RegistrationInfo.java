package ru.kotomore.EventPlanner.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "registration_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String fullName;

    private int age;

    private boolean pcrTestResult;

    private boolean isPaid;
}
