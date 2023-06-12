package ru.kotomore.EventPlanner.services;

import ru.kotomore.EventPlanner.models.Contract;
import ru.kotomore.EventPlanner.models.ContractStatus;
import ru.kotomore.EventPlanner.models.User;

import java.util.List;

public interface ContractService {
    void createContractRequest(User user);

    List<Contract> findByStatus(ContractStatus contractStatus);

    Contract changeStatus(Long contractId, ContractStatus contractStatus);
}
