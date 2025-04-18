import service.impl.AcquiringBankServiceImpl;
import model.AcquiringBank;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AcquiringBankServiceImplTest {
    private AcquiringBankServiceImpl service;
    private static final String TEST_BIC = "TESTBIC01";
    private static final String TEST_NAME = "Test Bank";

    @BeforeEach
    void setUp() {
        service = new AcquiringBankServiceImpl();
        service.clearAllAcquiringBanks();
    }

    @AfterEach
    void tearDown() {
        service.clearAllAcquiringBanks();
    }

    @Test
    void createAcquiringBank_ShouldAddNewBank() {
        AcquiringBank bank = new AcquiringBank(TEST_BIC, TEST_NAME);
        service.createAcquiringBank(bank);

        List<AcquiringBank> banks = service.getAllAcquiringBanks();
        assertEquals(1, banks.size());
        assertEquals(TEST_BIC, banks.get(0).getBic());
    }

    @Test
    void createAcquiringBank_ShouldThrowWhenBankIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createAcquiringBank(null));
    }

    @Test
    void getAcquiringBankById_ShouldReturnBankWhenExists() {
        AcquiringBank bank = new AcquiringBank(TEST_BIC, TEST_NAME);
        service.createAcquiringBank(bank);

        List<AcquiringBank> banks = service.getAllAcquiringBanks();
        Long createdId = banks.get(0).getId();

        Optional<AcquiringBank> foundBank = service.getAcquiringBankById(createdId);
        assertTrue(foundBank.isPresent());
        assertEquals(TEST_BIC, foundBank.get().getBic());
    }

    @Test
    void getAcquiringBankById_ShouldReturnEmptyWhenNotExists() {
        Optional<AcquiringBank> foundBank = service.getAcquiringBankById(999L);
        assertFalse(foundBank.isPresent());
    }

    @Test
    void getAllAcquiringBanks_ShouldReturnAllBanks() {
        service.createAcquiringBank(new AcquiringBank("BIC001", "Bank 1"));
        service.createAcquiringBank(new AcquiringBank("BIC002", "Bank 2"));

        List<AcquiringBank> banks = service.getAllAcquiringBanks();
        assertEquals(2, banks.size());
    }

    @Test
    void updateAcquiringBank_ShouldUpdateExistingBank() {
        AcquiringBank bank = new AcquiringBank(TEST_BIC, TEST_NAME);
        service.createAcquiringBank(bank);

        List<AcquiringBank> banks = service.getAllAcquiringBanks();
        AcquiringBank createdBank = banks.get(0);

        String updatedName = "Updated Bank Name";
        createdBank.setAbbreviatedName(updatedName);
        service.updateAcquiringBank(createdBank);

        Optional<AcquiringBank> updatedBank = service.getAcquiringBankById(createdBank.getId());
        assertTrue(updatedBank.isPresent());
        assertEquals(updatedName, updatedBank.get().getAbbreviatedName());
    }

    @Test
    void deleteAcquiringBank_ShouldRemoveBank() {
        AcquiringBank bank = new AcquiringBank(TEST_BIC, TEST_NAME);
        service.createAcquiringBank(bank);

        List<AcquiringBank> banks = service.getAllAcquiringBanks();
        Long createdId = banks.get(0).getId();

        service.deleteAcquiringBank(createdId);
        Optional<AcquiringBank> deletedBank = service.getAcquiringBankById(createdId);
        assertFalse(deletedBank.isPresent());
    }

    @Test
    void clearAllAcquiringBanks_ShouldRemoveAllBanks() {
        service.createAcquiringBank(new AcquiringBank("BIC001", "Bank 1"));
        service.createAcquiringBank(new AcquiringBank("BIC002", "Bank 2"));

        service.clearAllAcquiringBanks();
        List<AcquiringBank> banks = service.getAllAcquiringBanks();
        assertTrue(banks.isEmpty());
    }

    @Test
    void validateAcquiringBank_ShouldThrowWhenBicIsNull() {
        AcquiringBank bank = new AcquiringBank(null, TEST_NAME);
        assertThrows(IllegalArgumentException.class,
                () -> service.createAcquiringBank(bank));
    }

    @Test
    void validateAcquiringBank_ShouldThrowWhenNameIsNull() {
        AcquiringBank bank = new AcquiringBank(TEST_BIC, null);
        assertThrows(IllegalArgumentException.class,
                () -> service.createAcquiringBank(bank));
    }
}