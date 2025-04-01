package main.java.service;

import main.java.model.TransactionType;

import java.util.List;
import java.util.Optional;

public interface TransactionTypeService {
    void createTransactionType(TransactionType transactionType);
    Optional<TransactionType> getTransactionTypeById(Long id);
    List<TransactionType> getAllTransactionTypes();
    void updateTransactionType(TransactionType transactionType);
    void deleteTransactionType(Long id);
    void clearAllTransactionTypes();
    Optional<TransactionType> getTransactionTypeByName(String name);
    List<TransactionType> getTransactionTypesByOperator(String operator);
    boolean validateTransactionType(TransactionType transactionType);
}
