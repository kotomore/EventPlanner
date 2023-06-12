package ru.kotomore.EventPlanner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kotomore.EventPlanner.models.Contract;
import ru.kotomore.EventPlanner.models.ContractStatus;
import ru.kotomore.EventPlanner.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    boolean existsByUser(User user);
    Optional<Contract> findByUser(User user);
    List<Contract> findByContractStatus(ContractStatus contractStatus);
}
