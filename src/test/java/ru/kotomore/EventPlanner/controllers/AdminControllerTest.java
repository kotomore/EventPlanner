package ru.kotomore.EventPlanner.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.kotomore.EventPlanner.models.Contract;
import ru.kotomore.EventPlanner.models.ContractStatus;
import ru.kotomore.EventPlanner.services.ContractService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTest {

    @Mock
    private ContractService contractService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        contractService = Mockito.mock(ContractService.class);

    }

    @Test
    public void testChangeContractStatus_ShouldReturnOk() throws Exception {
        AdminController adminController = new AdminController(contractService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        Long contractId = 1L;
        ContractStatus newStatus = ContractStatus.ACCEPTED;
        Contract updatedContract = new Contract();

        when(contractService.changeStatus(contractId, newStatus)).thenReturn(updatedContract);

        mockMvc.perform(put("/api/admin/contracts/{id}", contractId)
                        .param("contractStatus", newStatus.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(contractService).changeStatus(contractId, newStatus);
    }

    @Test
    public void testFindByStatus_ShouldReturnOkWithContracts() throws Exception {
        AdminController adminController = new AdminController(contractService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        ContractStatus contractStatus = ContractStatus.PENDING;
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());

        when(contractService.findByStatus(contractStatus)).thenReturn(contracts);

        mockMvc.perform(get("/api/admin/contracts")
                        .param("contractStatus", contractStatus.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(contracts.size()));

        verify(contractService).findByStatus(contractStatus);
    }

    @Test
    public void testFindByStatus_ShouldReturnNoContent() throws Exception {
        AdminController adminController = new AdminController(contractService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

        ContractStatus contractStatus = ContractStatus.ACCEPTED;

        when(contractService.findByStatus(contractStatus)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/admin/contracts")
                        .param("contractStatus", contractStatus.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(contractService).findByStatus(contractStatus);
    }
}
