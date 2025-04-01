package main.java.service.impl;

import main.java.dao.Dao;
import main.java.dao.jdbc.TransactionJDBCDaoImpl;
import main.java.model.Transaction;
import main.java.service.TransactionService;

import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {
    private final Dao<Transaction> transactionDao;

    public TransactionServiceImpl() {
        this.transactionDao = new TransactionJDBCDaoImpl();
    }

    @Override
    public void createTransaction(Transaction transaction) {
        validateTransaction(transaction);
        transactionDao.add(transaction);
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid transaction ID");
        }
        return transactionDao.getById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDao.getAll();
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        validateTransaction(transaction);
        transactionDao.update(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid transaction ID");
        }
        transactionDao.deleteById(id);
    }

    @Override
    public void clearAllTransactions() {
        transactionDao.clearTable();
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (transaction.getSum() <= 0) {
            throw new IllegalArgumentException("Transaction sum must be positive");
        }
        if (transaction.getTransactionName() == null || transaction.getTransactionName().isEmpty()) {
            throw new IllegalArgumentException("Transaction name cannot be empty");
        }
    }
}
