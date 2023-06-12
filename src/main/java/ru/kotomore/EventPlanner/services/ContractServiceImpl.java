package ru.kotomore.EventPlanner.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kotomore.EventPlanner.models.Contract;
import ru.kotomore.EventPlanner.models.ContractStatus;
import ru.kotomore.EventPlanner.models.User;
import ru.kotomore.EventPlanner.repositories.ContractRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;

    @Override
    public void createContractRequest(User user) {
        if (contractRepository.existsByUser(user)) {
            throw new RuntimeException("Заявка на заключение договора уже отправлена");
        }

        Contract contract = new Contract();
        contract.setUser(user);
        contract.setContractStatus(ContractStatus.PENDING);
        contractRepository.save(contract);
    }

    @Override
    public List<Contract> findByStatus(ContractStatus contractStatus) {
        List<Contract> contracts = contractRepository.findByContractStatus(contractStatus);
        if (contracts.isEmpty()) {
            throw new RuntimeException("Договоров не найдено");
        }
        return contracts;
    }

    public Contract changeStatus(Long contractId, ContractStatus contractStatus) {
        Contract editedContract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Договор не найден"));
        editedContract.setContractStatus(contractStatus);
        return contractRepository.save(editedContract);
    }
}
