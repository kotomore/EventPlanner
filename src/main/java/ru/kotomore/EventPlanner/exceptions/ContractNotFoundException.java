package ru.kotomore.EventPlanner.exceptions;

public class ContractNotFoundException extends RuntimeException {
    public ContractNotFoundException(Long id) {
        super(String.format("Договор с ID - %d не найден", id));
    }
}
