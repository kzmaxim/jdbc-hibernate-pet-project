import dao.Dao;
import model.MerchantCategoryCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.MerchantCategoryCodeServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MerchantCategoryCodeServiceImplTest {
    private Dao<MerchantCategoryCode> mccDao;
    private MerchantCategoryCodeServiceImpl service;

    @BeforeEach
    void setUp() {
        mccDao = mock(Dao.class);
        service = new MerchantCategoryCodeServiceImpl(mccDao); // используй этот конструктор
    }

    @Test
    void testCreateMCC() {
        MerchantCategoryCode mcc = new MerchantCategoryCode("1234", "Restaurants");
        service.createMCC(mcc);
        verify(mccDao).add(mcc);
    }

    @Test
    void testCreateMCC_Null_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.createMCC(null);
        });
    }

    @Test
    void testGetMCCById() {
        MerchantCategoryCode mcc = new MerchantCategoryCode("1234", "Cafes");
        mcc.setId(1L);
        when(mccDao.getById(1L)).thenReturn(Optional.of(mcc));

        Optional<MerchantCategoryCode> result = service.getMCCById(1L);

        assertTrue(result.isPresent());
        assertEquals("Cafes", result.get().getMccName());
    }

    @Test
    void testGetMCCById_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getMCCById(0L);
        });
    }

    @Test
    void testGetAllMCCs() {
        List<MerchantCategoryCode> list = List.of(
                new MerchantCategoryCode("1111", "Hotels"),
                new MerchantCategoryCode("2222", "Airlines")
        );
        when(mccDao.getAll()).thenReturn(list);

        List<MerchantCategoryCode> result = service.getAllMCCs();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateMCC() {
        MerchantCategoryCode mcc = new MerchantCategoryCode("5678", "Shops");
        mcc.setId(2L);
        service.updateMCC(mcc);
        verify(mccDao).update(mcc);
    }

    @Test
    void testDeleteMCC() {
        service.deleteMCC(1L);
        verify(mccDao).deleteById(1L);
    }

    @Test
    void testDeleteMCC_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteMCC(0L);
        });
    }

    @Test
    void testClearAllMCCs() {
        service.clearAllMCCs();
        verify(mccDao).clearTable();
    }

    @Test
    void testGetMCCByCode() {
        List<MerchantCategoryCode> list = List.of(
                new MerchantCategoryCode("1234", "Transport"),
                new MerchantCategoryCode("5678", "Groceries")
        );
        when(mccDao.getAll()).thenReturn(list);

        Optional<MerchantCategoryCode> result = service.getMCCByCode("5678");

        assertTrue(result.isPresent());
        assertEquals("Groceries", result.get().getMccName());
    }

    @Test
    void testGetMCCByCode_Empty_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getMCCByCode("");
        });
    }
}

