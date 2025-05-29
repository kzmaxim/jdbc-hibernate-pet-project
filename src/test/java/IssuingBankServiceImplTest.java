import dao.Dao;
import model.IssuingBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.impl.IssuingBankServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IssuingBankServiceImplTest {
    private Dao<IssuingBank> issuingBankDao;
    private IssuingBankServiceImpl service;

    @BeforeEach
    void setUp() {
        issuingBankDao = mock(Dao.class);
        service = new IssuingBankServiceImpl(issuingBankDao); // конструктор с дао
    }

    @Test
    void testCreateIssuingBank() {
        IssuingBank bank = new IssuingBank("123456", "123456789", "MyBank");
        service.createIssuingBank(bank);
        verify(issuingBankDao).add(bank);
    }

    @Test
    void testCreateIssuingBank_Null_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.createIssuingBank(null);
        });
    }

    @Test
    void testGetIssuingBankById() {
        IssuingBank bank = new IssuingBank("123456", "123456789", "Bank1");
        bank.setId(1L);
        when(issuingBankDao.getById(1L)).thenReturn(Optional.of(bank));

        Optional<IssuingBank> result = service.getIssuingBankById(1L);

        assertTrue(result.isPresent());
        assertEquals("Bank1", result.get().getAbbreviatedName());
    }

    @Test
    void testGetIssuingBankById_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getIssuingBankById(0L);
        });
    }

    @Test
    void testGetAllIssuingBanks() {
        List<IssuingBank> list = List.of(
                new IssuingBank("111111", "111111111", "BankA"),
                new IssuingBank("222222", "222222222", "BankB")
        );
        when(issuingBankDao.getAll()).thenReturn(list);

        List<IssuingBank> result = service.getAllIssuingBanks();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateIssuingBank() {
        IssuingBank bank = new IssuingBank("123456", "123456789", "Bank2");
        bank.setId(1L);
        service.updateIssuingBank(bank);
        verify(issuingBankDao).update(bank);
    }

    @Test
    void testDeleteIssuingBank() {
        service.deleteIssuingBank(1L);
        verify(issuingBankDao).deleteById(1L);
    }

    @Test
    void testDeleteIssuingBank_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteIssuingBank(0L);
        });
    }

    @Test
    void testClearAllIssuingBanks() {
        service.clearAllIssuingBanks();
        verify(issuingBankDao).clearTable();
    }

    @Test
    void testGetIssuingBankByBic() {
        List<IssuingBank> banks = List.of(
                new IssuingBank("123456", "123456789", "Bank1"),
                new IssuingBank("654321", "987654321", "Bank2")
        );
        when(issuingBankDao.getAll()).thenReturn(banks);

        Optional<IssuingBank> result = service.getIssuingBankByBic("123456");

        assertTrue(result.isPresent());
        assertEquals("Bank1", result.get().getAbbreviatedName());
    }

    @Test
    void testGetIssuingBankByBic_Empty_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getIssuingBankByBic("");
        });
    }

    @Test
    void testGetIssuingBankByBin() {
        List<IssuingBank> banks = List.of(
                new IssuingBank("111111", "999999999", "BankX"),
                new IssuingBank("222222", "888888888", "BankY")
        );
        when(issuingBankDao.getAll()).thenReturn(banks);

        Optional<IssuingBank> result = service.getIssuingBankByBin("999999999");

        assertTrue(result.isPresent());
        assertEquals("BankX", result.get().getAbbreviatedName());
    }

    @Test
    void testGetIssuingBankByBin_Empty_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getIssuingBankByBin("");
        });
    }
}

