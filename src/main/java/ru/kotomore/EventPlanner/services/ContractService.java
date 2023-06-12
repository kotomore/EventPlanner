package ru.kotomore.EventPlanner.services;

import ru.kotomore.EventPlanner.models.Contract;
import ru.kotomore.EventPlanner.models.ContractStatus;
import ru.kotomore.EventPlanner.models.User;

import java.util.List;

public interface ContractService {
    /**
     * Отправить заявку на заключение договора
     *
     * @param user Пользователь, который отправляет заявку
     */
    void createContractRequest(User user);

    /**
     * Найти все заявки по их статусу
     *
     * @param contractStatus статус заявки на договор
     * @return Список договоров с указанным статусом
     */
    List<Contract> findByStatus(ContractStatus contractStatus);

    /**
     * Изменить статус договора
     *
     * @param contractId      ID договора статус которого необходимо изменить
     * @param contractStatus  Новый статус для договора
     * @return Измененный договор
     */
    Contract changeStatus(Long contractId, ContractStatus contractStatus);
}
