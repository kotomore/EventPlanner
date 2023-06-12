package ru.kotomore.EventPlanner.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.kotomore.EventPlanner.exceptions.ContractNotFoundException;
import ru.kotomore.EventPlanner.exceptions.ContractRequestAlreadySentException;
import ru.kotomore.EventPlanner.models.Contract;
import ru.kotomore.EventPlanner.models.ContractStatus;
import ru.kotomore.EventPlanner.models.Role;
import ru.kotomore.EventPlanner.models.User;
import ru.kotomore.EventPlanner.repositories.ContractRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

    @BeforeEach
    public void setUp() {
        contractRepository = Mockito.mock(ContractRepository.class);

    }

    @Test
    public void testCreateContractRequest_WithNoExistingRequest_ShouldCreateContract() {
        ContractServiceImpl contractService = new ContractServiceImpl(contractRepository);

        User user = getUser();
        Contract contract = new Contract();
        contract.setUser(user);
        contract.setContractStatus(ContractStatus.PENDING);

        when(contractRepository.existsByUser(user)).thenReturn(false);

        contractService.createContractRequest(user);

        verify(contractRepository, Mockito.times(1)).save(Mockito.any(Contract.class));
    }

    @Test
    public void testCreateContractRequest_WithExistingRequest_ShouldThrowContractRequestAlreadySentException() {
        ContractServiceImpl contractService = new ContractServiceImpl(contractRepository);

        User user = getUser();

        when(contractRepository.existsByUser(user)).thenReturn(true);

        assertThrows(ContractRequestAlreadySentException.class, () -> contractService.createContractRequest(user));

        verify(contractRepository, never()).save(any());
    }

    @Test
    public void testChangeStatus_WithExistingContract_ShouldChangeStatus() {
        ContractServiceImpl contractService = new ContractServiceImpl(contractRepository);

        Long contractId = 1L;
        ContractStatus newStatus = ContractStatus.ACCEPTED;

        Contract existingContract = new Contract();
        existingContract.setId(contractId);
        existingContract.setContractStatus(ContractStatus.PENDING);

        Contract updatedContract = new Contract();
        updatedContract.setId(contractId);
        updatedContract.setContractStatus(newStatus);

        when(contractRepository.findById(contractId)).thenReturn(Optional.of(existingContract));
        when(contractRepository.save(existingContract)).thenReturn(updatedContract);

        Contract result = contractService.changeStatus(contractId, newStatus);

        assertEquals(updatedContract, result);
        assertEquals(newStatus, result.getContractStatus());

        verify(contractRepository).findById(contractId);
        verify(contractRepository).save(existingContract);
    }

    @Test
    public void testChangeStatus_WithNonExistingContract_ShouldThrowContractNotFoundException() {
        ContractServiceImpl contractService = new ContractServiceImpl(contractRepository);

        Long contractId = 1L;
        ContractStatus newStatus = ContractStatus.ACCEPTED;

        when(contractRepository.findById(contractId)).thenReturn(Optional.empty());

        assertThrows(ContractNotFoundException.class, () -> contractService.changeStatus(contractId, newStatus));

        verify(contractRepository).findById(contractId);
        verify(contractRepository, never()).save(any());
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("John");
        user.setPassword("Password");
        user.setRole(Role.ROLE_USER);
        return user;
    }
}