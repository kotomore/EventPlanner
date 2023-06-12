package ru.kotomore.EventPlanner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kotomore.EventPlanner.models.RegistrationInfo;

@Repository
public interface RegistrationInfoRepository extends JpaRepository<RegistrationInfo, Long> {
}
