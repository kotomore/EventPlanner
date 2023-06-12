package ru.kotomore.EventPlanner.exceptions;

public class ContractNotAcceptedException extends RuntimeException {
    public ContractNotAcceptedException() {
        super("Договор еще не заключен или находится в статусе ожидания");
    }
}
