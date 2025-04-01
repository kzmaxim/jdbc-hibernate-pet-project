package main.java.service;

import main.java.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    void createTransaction(Transaction transaction);
    Optional<Transaction> getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    void updateTransaction(Transaction transaction);
    void deleteTransaction(Long id);
    void clearAllTransactions();
}