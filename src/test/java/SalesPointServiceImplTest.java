import dao.Dao;
import model.AcquiringBank;
import model.SalesPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.SalesPointServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SalesPointServiceImplTest {

    private Dao<SalesPoint> salesPointDao;
    private SalesPointServiceImpl salesPointService;

    @BeforeEach
    public void setUp() {
        salesPointDao = mock(Dao.class);
        salesPointService = new SalesPointServiceImpl(salesPointDao);
    }

    private SalesPoint buildValidSalesPoint() {
        AcquiringBank bank = new AcquiringBank();
        bank.setId(1L);
        return new SalesPoint("Shop1", "Address1", "1234567890", bank);
    }

    @Test
    public void testCreateSalesPoint() {
        SalesPoint sp = buildValidSalesPoint();
        salesPointService.createSalesPoint(sp);
        verify(salesPointDao).add(sp);
    }

    @Test
    public void testGetSalesPointsByBankId_Valid() {
        AcquiringBank bank1 = new AcquiringBank();
        bank1.setId(1L);
        AcquiringBank bank2 = new AcquiringBank();
        bank2.setId(2L);

        SalesPoint sp1 = new SalesPoint("Shop1", "Addr1", "1234567890", bank1);
        SalesPoint sp2 = new SalesPoint("Shop2", "Addr2", "0987654321", bank1);
        SalesPoint sp3 = new SalesPoint("Shop3", "Addr3", "1111111111", bank2);

        when(salesPointDao.getAll()).thenReturn(Arrays.asList(sp1, sp2, sp3));

        List<SalesPoint> result = salesPointService.getSalesPointsByBankId(1L);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(sp -> sp.getAcquiringBankId().getId().equals(1L)));
    }

    @Test
    public void testValidateSalesPoint_NullBank_ThrowsException() {
        SalesPoint sp = new SalesPoint("Shop", "Address", "1234567890", null);
        assertThrows(IllegalArgumentException.class, () -> salesPointService.validateSalesPoint(sp));
    }

    @Test
    public void testValidateSalesPoint_InvalidINN_ThrowsException() {
        AcquiringBank bank = new AcquiringBank();
        bank.setId(1L);
        SalesPoint sp = new SalesPoint("Shop", "Address", "123", bank);
        assertThrows(IllegalArgumentException.class, () -> salesPointService.validateSalesPoint(sp));
    }

    @Test
    public void testDeleteSalesPoint_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> salesPointService.deleteSalesPoint(0L));
        assertThrows(IllegalArgumentException.class, () -> salesPointService.deleteSalesPoint(null));
    }

    @Test
    public void testGetSalesPointById_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> salesPointService.getSalesPointById(-1L));
        assertThrows(IllegalArgumentException.class, () -> salesPointService.getSalesPointById(null));
    }

    @Test
    public void testGetSalesPointById_ValidId() {
        SalesPoint sp = buildValidSalesPoint();
        when(salesPointDao.getById(1L)).thenReturn(Optional.of(sp));

        Optional<SalesPoint> result = salesPointService.getSalesPointById(1L);
        assertTrue(result.isPresent());
        assertEquals("Shop1", result.get().getPosName());
    }
}
