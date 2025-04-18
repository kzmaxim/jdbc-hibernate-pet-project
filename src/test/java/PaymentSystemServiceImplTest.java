import dao.Dao;
import model.PaymentSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.impl.PaymentSystemServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentSystemServiceImplTest {

    private Dao<PaymentSystem> paymentSystemDao;
    private PaymentSystemServiceImpl paymentSystemService;

    @BeforeEach
    public void setUp() {
        paymentSystemDao = Mockito.mock(Dao.class);
        paymentSystemService = new PaymentSystemServiceImpl(paymentSystemDao);
    }

    @Test
    public void testCreatePaymentSystem() {
        PaymentSystem ps = new PaymentSystem("Visa");
        paymentSystemService.createPaymentSystem(ps);
        verify(paymentSystemDao).add(ps);
    }

    @Test
    public void testCreatePaymentSystem_Null_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            paymentSystemService.createPaymentSystem(null);
        });
    }

    @Test
    public void testGetPaymentSystemById_ValidId() {
        PaymentSystem ps = new PaymentSystem("MasterCard");
        when(paymentSystemDao.getById(1L)).thenReturn(Optional.of(ps));

        Optional<PaymentSystem> result = paymentSystemService.getPaymentSystemById(1L);
        assertTrue(result.isPresent());
        assertEquals("MasterCard", result.get().getPaymentSystemName());
    }

    @Test
    public void testGetPaymentSystemById_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            paymentSystemService.getPaymentSystemById(0L);
        });
    }

    @Test
    public void testGetAllPaymentSystems() {
        PaymentSystem ps1 = new PaymentSystem("Visa");
        PaymentSystem ps2 = new PaymentSystem("MasterCard");
        when(paymentSystemDao.getAll()).thenReturn(Arrays.asList(ps1, ps2));

        List<PaymentSystem> result = paymentSystemService.getAllPaymentSystems();
        assertEquals(2, result.size());
    }

    @Test
    public void testUpdatePaymentSystem() {
        PaymentSystem ps = new PaymentSystem("Visa");
        paymentSystemService.updatePaymentSystem(ps);
        verify(paymentSystemDao).update(ps);
    }

    @Test
    public void testDeletePaymentSystem() {
        paymentSystemService.deletePaymentSystem(5L);
        verify(paymentSystemDao).deleteById(5L);
    }

    @Test
    public void testDeletePaymentSystem_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            paymentSystemService.deletePaymentSystem(-1L);
        });
    }

    @Test
    public void testClearAllPaymentSystems() {
        paymentSystemService.clearAllPaymentSystems();
        verify(paymentSystemDao).clearTable();
    }

    @Test
    public void testGetPaymentSystemByName_Found() {
        PaymentSystem ps = new PaymentSystem("Visa");
        when(paymentSystemDao.getAll()).thenReturn(List.of(ps));

        Optional<PaymentSystem> result = paymentSystemService.getPaymentSystemByName("Visa");
        assertTrue(result.isPresent());
    }

    @Test
    public void testGetPaymentSystemByName_EmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            paymentSystemService.getPaymentSystemByName("");
        });
    }
}
