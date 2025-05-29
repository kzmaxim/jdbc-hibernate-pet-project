import model.Account;
import dao.Dao;
import model.IssuingBank;
import model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.AccountServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    private Dao<Account> mockAccountDao;
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        mockAccountDao = mock(Dao.class);
        accountService = new AccountServiceImpl(mockAccountDao); // добавь конструктор с dao в реализацию
    }

    @Test
    void createAccount_validAccount_callsAdd() {
        Account account = createValidAccount();
        accountService.createAccount(account);
        verify(mockAccountDao).add(account);
    }

    @Test
    void createAccount_nullAccount_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(null));
    }

    @Test
    void getAccountById_validId_returnsAccount() {
        Account account = createValidAccount();
        when(mockAccountDao.getById(1L)).thenReturn(Optional.of(account));
        Optional<Account> result = accountService.getAccountById(1L);
        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    void getAccountById_invalidId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> accountService.getAccountById(-5L));
    }

    @Test
    void getAllAccounts_returnsList() {
        List<Account> accounts = List.of(createValidAccount(), createValidAccount());
        when(mockAccountDao.getAll()).thenReturn(accounts);
        List<Account> result = accountService.getAllAccounts();
        assertEquals(2, result.size());
    }

    @Test
    void updateAccount_validAccount_callsUpdate() {
        Account account = createValidAccount();
        accountService.updateAccount(account);
        verify(mockAccountDao).update(account);
    }

    @Test
    void deleteAccount_validId_callsDeleteById() {
        accountService.deleteAccount(1L);
        verify(mockAccountDao).deleteById(1L);
    }

    @Test
    void deleteAccount_invalidId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> accountService.deleteAccount(0L));
    }

    @Test
    void transferFunds_successfulTransfer_returnsTrue() {
        Account from = createValidAccount();
        from.setBalance(500.0);
        Account to = createValidAccount();
        to.setBalance(100.0);

        when(mockAccountDao.getById(1L)).thenReturn(Optional.of(from));
        when(mockAccountDao.getById(2L)).thenReturn(Optional.of(to));

        boolean result = accountService.transferFunds(1L, 2L, 200.0);

        assertTrue(result);
        assertEquals(300.0, from.getBalance());
        assertEquals(300.0, to.getBalance());
        verify(mockAccountDao).update(from);
        verify(mockAccountDao).update(to);
    }

    @Test
    void transferFunds_insufficientFunds_returnsFalse() {
        Account from = createValidAccount();
        from.setBalance(100.0);
        Account to = createValidAccount();
        to.setBalance(100.0);

        when(mockAccountDao.getById(1L)).thenReturn(Optional.of(from));
        when(mockAccountDao.getById(2L)).thenReturn(Optional.of(to));

        boolean result = accountService.transferFunds(1L, 2L, 200.0);
        assertFalse(result);
    }

    @Test
    void getBalance_validId_returnsBalance() {
        Account account = createValidAccount();
        account.setBalance(999.0);
        when(mockAccountDao.getById(1L)).thenReturn(Optional.of(account));
        double balance = accountService.getBalance(1L);
        assertEquals(999.0, balance);
    }

    @Test
    void getBalance_accountNotFound_throwsException() {
        when(mockAccountDao.getById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> accountService.getBalance(1L));
    }

    private Account createValidAccount() {
        Account account = new Account();
        account.setAccountNumber("12345678");


        Currency currency = new Currency("840", "USD", "US Dollar");
        currency.setId(1L);

        account.setCurrencyId(currency);

        IssuingBank issuingBank = new IssuingBank("041234569", "12345", "Test Bank");
        issuingBank.setId(1L);

        account.setIssuingBankId(issuingBank);

        return account;
    }

}
