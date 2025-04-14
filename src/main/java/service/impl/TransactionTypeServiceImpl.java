package service.impl;

import dao.Dao;
import dao.hibernate.TransactionTypeHibernateDaoImpl;
import dao.jdbc.TransactionTypeJDBCDaoImpl;
import model.TransactionType;
import service.TransactionTypeService;

import java.util.List;
import java.util.Optional;

public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final Dao<TransactionType> transactionTypeDao;

    public TransactionTypeServiceImpl() {
        this.transactionTypeDao = new TransactionTypeHibernateDaoImpl();
    }

    @Override
    public void createTransactionType(TransactionType transactionType) {
        validateTransactionType(transactionType);
        transactionTypeDao.add(transactionType);
    }

    @Override
    public Optional<TransactionType> getTransactionTypeById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid transaction type ID");
        }
        return transactionTypeDao.getById(id);
    }

    @Override
    public List<TransactionType> getAllTransactionTypes() {
        return transactionTypeDao.getAll();
    }

    @Override
    public void updateTransactionType(TransactionType transactionType) {
        validateTransactionType(transactionType);
        transactionTypeDao.update(transactionType);
    }

    @Override
    public void deleteTransactionType(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid transaction type ID");
        }
        transactionTypeDao.deleteById(id);
    }

    @Override
    public void clearAllTransactionTypes() {
        transactionTypeDao.clearTable();
    }

    @Override
    public Optional<TransactionType> getTransactionTypeByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Transaction type name cannot be empty");
        }
        return transactionTypeDao.getAll().stream()
                .filter(tt -> name.equalsIgnoreCase(tt.getTransactionTypeName()))
                .findFirst();
    }

    @Override
    public List<TransactionType> getTransactionTypesByOperator(String operator) {
        if (operator == null || operator.isEmpty()) {
            throw new IllegalArgumentException("Operator cannot be empty");
        }
        return transactionTypeDao.getAll().stream()
                .filter(tt -> operator.equals(tt.getOperator()))
                .toList();
    }

    @Override
    public boolean validateTransactionType(TransactionType transactionType) {
        if (transactionType == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        if (transactionType.getTransactionTypeName() == null ||
                transactionType.getTransactionTypeName().isEmpty()) {
            throw new IllegalArgumentException("Transaction type name is required");
        }
        if (transactionType.getOperator() == null ||
                !(transactionType.getOperator().equals("+") || transactionType.getOperator().equals("-"))) {
            throw new IllegalArgumentException("Operator must be either '+' or '-'");
        }
        return true;
    }
}