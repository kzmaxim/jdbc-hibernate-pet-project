package service.impl;

import main.java.dao.Dao;
import dao.jdbc.AccountJDBCDaoImpl;
import model.Account;
import service.AccountService;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private final Dao<Account> accountDao;

    public AccountServiceImpl() {
        this.accountDao = new AccountJDBCDaoImpl();
    }

    @Override
    public void createAccount(Account account) {
        validateAccount(account);
        accountDao.add(account);
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        return accountDao.getById(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.getAll();
    }

    @Override
    public void updateAccount(Account account) {
        validateAccount(account);
        accountDao.update(account);
    }

    @Override
    public void deleteAccount(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        accountDao.deleteById(id);
    }

    @Override
    public void clearAllAccounts() {
        accountDao.clearTable();
    }

    @Override
    public boolean transferFunds(Long fromAccountId, Long toAccountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Optional<Account> fromAccountOpt = accountDao.getById(fromAccountId);
        Optional<Account> toAccountOpt = accountDao.getById(toAccountId);

        if (fromAccountOpt.isEmpty() || toAccountOpt.isEmpty()) {
            throw new IllegalArgumentException("One or both accounts not found");
        }

        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();

        if (fromAccount.getBalance() < amount) {
            return false;
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountDao.update(fromAccount);
        accountDao.update(toAccount);

        return true;
    }

    @Override
    public double getBalance(Long accountId) {
        Optional<Account> account = accountDao.getById(accountId);
        return account.map(Account::getBalance).orElseThrow(() ->
                new IllegalArgumentException("Account not found")
        );
    }

    private void validateAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (account.getAccountNumber() == null || account.getAccountNumber().isEmpty()) {
            throw new IllegalArgumentException("Account number is required");
        }
        if (account.getCurrencyId() == null || account.getCurrencyId() <= 0) {
            throw new IllegalArgumentException("Invalid currency ID");
        }
        if (account.getIssuingBankId() == null || account.getIssuingBankId() <= 0) {
            throw new IllegalArgumentException("Invalid bank ID");
        }
    }
}

