package ru.kotomore.EventPlanner.exceptions;

public class ContractRequestAlreadySentException extends RuntimeException {
    public ContractRequestAlreadySentException() {
        super("Заявка на заключение договора уже отправлена");
    }
}
