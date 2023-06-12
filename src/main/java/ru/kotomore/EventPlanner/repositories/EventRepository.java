package ru.kotomore.EventPlanner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kotomore.EventPlanner.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
