package main.java.service;

import main.java.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    void createAccount(Account account);
    Optional<Account> getAccountById(Long id);
    List<Account> getAllAccounts();
    void updateAccount(Account account);
    void deleteAccount(Long id);
    void clearAllAccounts();

    boolean transferFunds(Long fromAccountId, Long toAccountId, double amount);
    double getBalance(Long accountId);
}