import model.Account;
import model.Currency;
import model.IssuingBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.AccountServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

    private AccountServiceImpl accountService;

    private Currency testCurrency;
    private IssuingBank testBank;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl();
        accountService.clearAllAccounts(); // очистка перед каждым тестом

        testCurrency = new Currency("840", "USD", "US Dollar");
        testBank =  new IssuingBank("041234569", "12345", "Test Bank");
    }

    private Account createTestAccount(String number, double balance) {
        Account account = new Account();
        account.setAccountNumber(number);
        account.setCurrencyId(testCurrency);
        account.setIssuingBankId(testBank);
        account.setBalance(balance);
        return account;
    }

    @Test
    void createAccount_ShouldAddAccountSuccessfully() {
        Account account = createTestAccount("123456", 1000.0);

        accountService.createAccount(account);
        List<Account> accounts = accountService.getAllAccounts();

        assertEquals(1, accounts.size());
        assertEquals("123456", accounts.get(0).getAccountNumber());
    }

    @Test
    void createAccount_ShouldThrow_WhenAccountNumberIsMissing() {
        Account account = createTestAccount(null, 100.0);

        assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(account));
    }

    @Test
    void getAccountById_ShouldReturnCorrectAccount() {
        Account account = createTestAccount("654321", 500.0);
        accountService.createAccount(account);
        Long id = accountService.getAllAccounts().get(0).getId();

        Optional<Account> result = accountService.getAccountById(id);

        assertTrue(result.isPresent());
        assertEquals("654321", result.get().getAccountNumber());
    }

    @Test
    void transferFunds_ShouldTransferMoney_WhenEnoughBalance() {
        Account from = createTestAccount("111", 1000);
        Account to = createTestAccount("222", 500);

        accountService.createAccount(from);
        accountService.createAccount(to);

        Long fromId = accountService.getAllAccounts().get(0).getId();
        Long toId = accountService.getAllAccounts().get(1).getId();

        boolean success = accountService.transferFunds(fromId, toId, 300);

        assertTrue(success);
        assertEquals(700, accountService.getBalance(fromId));
        assertEquals(800, accountService.getBalance(toId));
    }

    @Test
    void transferFunds_ShouldFail_WhenInsufficientFunds() {
        Account from = createTestAccount("111", 100);
        Account to = createTestAccount("222", 500);

        accountService.createAccount(from);
        accountService.createAccount(to);

        Long fromId = accountService.getAllAccounts().get(0).getId();
        Long toId = accountService.getAllAccounts().get(1).getId();

        boolean success = accountService.transferFunds(fromId, toId, 300);

        assertFalse(success);
    }

    @Test
    void getBalance_ShouldReturnCorrectValue() {
        Account account = createTestAccount("333", 750.50);
        accountService.createAccount(account);
        Long id = accountService.getAllAccounts().get(0).getId();

        double balance = accountService.getBalance(id);

        assertEquals(750.50, balance);
    }

    @Test
    void deleteAccount_ShouldRemoveIt() {
        Account account = createTestAccount("444", 100);
        accountService.createAccount(account);
        Long id = accountService.getAllAccounts().get(0).getId();

        accountService.deleteAccount(id);

        assertTrue(accountService.getAccountById(id).isEmpty());
    }

    @Test
    void clearAllAccounts_ShouldEmptyDatabase() {
        accountService.createAccount(createTestAccount("1", 100));
        accountService.createAccount(createTestAccount("2", 200));

        accountService.clearAllAccounts();

        assertEquals(0, accountService.getAllAccounts().size());
    }
}

