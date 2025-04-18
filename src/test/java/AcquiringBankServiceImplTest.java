import service.impl.AcquiringBankServiceImpl;
import model.AcquiringBank;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import dao.Dao;
import model.AcquiringBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.AcquiringBankServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AcquiringBankServiceImplTest {

    private Dao<AcquiringBank> mockAcquiringBankDao;
    private AcquiringBankServiceImpl acquiringBankService;

    @BeforeEach
    void setUp() {
        mockAcquiringBankDao = mock(Dao.class);
        acquiringBankService = new AcquiringBankServiceImpl(mockAcquiringBankDao);
    }

    @Test
    void createAcquiringBank_valid_callsAdd() {
        AcquiringBank bank = createValidBank();
        acquiringBankService.createAcquiringBank(bank);
        verify(mockAcquiringBankDao).add(bank);
    }

    @Test
    void createAcquiringBank_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> acquiringBankService.createAcquiringBank(null));
    }

    @Test
    void getAcquiringBankById_valid_returnsOptional() {
        AcquiringBank bank = createValidBank();
        when(mockAcquiringBankDao.getById(1L)).thenReturn(Optional.of(bank));
        Optional<AcquiringBank> result = acquiringBankService.getAcquiringBankById(1L);
        assertTrue(result.isPresent());
        assertEquals(bank, result.get());
    }

    @Test
    void getAcquiringBankById_invalidId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> acquiringBankService.getAcquiringBankById(0L));
    }

    @Test
    void getAllAcquiringBanks_returnsList() {
        List<AcquiringBank> banks = List.of(createValidBank(), createValidBank());
        when(mockAcquiringBankDao.getAll()).thenReturn(banks);
        List<AcquiringBank> result = acquiringBankService.getAllAcquiringBanks();
        assertEquals(2, result.size());
    }

    @Test
    void updateAcquiringBank_valid_callsUpdate() {
        AcquiringBank bank = createValidBank();
        acquiringBankService.updateAcquiringBank(bank);
        verify(mockAcquiringBankDao).update(bank);
    }

    @Test
    void deleteAcquiringBank_valid_callsDeleteById() {
        acquiringBankService.deleteAcquiringBank(1L);
        verify(mockAcquiringBankDao).deleteById(1L);
    }

    @Test
    void deleteAcquiringBank_invalid_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> acquiringBankService.deleteAcquiringBank(0L));
    }

    @Test
    void clearAllAcquiringBanks_callsClearTable() {
        acquiringBankService.clearAllAcquiringBanks();
        verify(mockAcquiringBankDao).clearTable();
    }

    private AcquiringBank createValidBank() {
        AcquiringBank bank = new AcquiringBank();
        bank.setBic("123456789");
        bank.setAbbreviatedName("TESTBANK");
        return bank;
    }
}
