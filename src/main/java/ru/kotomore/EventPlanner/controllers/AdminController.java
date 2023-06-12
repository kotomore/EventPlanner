package ru.kotomore.EventPlanner.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kotomore.EventPlanner.models.Contract;
import ru.kotomore.EventPlanner.models.ContractStatus;
import ru.kotomore.EventPlanner.services.ContractService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@AllArgsConstructor
public class AdminController {
    private final ContractService contractService;

    @PutMapping("/contracts/{id}")
    @Operation(summary = "Изменение статуса договора с администратором мероприятия")
    public ResponseEntity<?> changeContractStatus(@PathVariable Long id, @RequestParam ContractStatus contractStatus) {

        Contract contract = contractService.changeStatus(id, contractStatus);

        return ResponseEntity.ok(contract);
    }

    @GetMapping("/contracts")
    @Operation(summary = "Поиск всех договоров по их статусу")
    public ResponseEntity<?> findByStatus(@RequestParam ContractStatus contractStatus) {

        List<Contract> contracts = contractService.findByStatus(contractStatus);

        if (contracts.isEmpty()) {
            ResponseEntity.noContent();
        }

        return ResponseEntity.ok(contracts);
    }
}
