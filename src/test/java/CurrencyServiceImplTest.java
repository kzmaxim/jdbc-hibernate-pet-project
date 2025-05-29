import dao.Dao;
import org.junit.jupiter.api.*;
import service.impl.CurrencyServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import model.Currency;

class CurrencyServiceImplTest {
    private Dao<Currency> currencyDao;
    private CurrencyServiceImpl service;

    @BeforeEach
    void setUp() {
        currencyDao = mock(Dao.class);
        service = new CurrencyServiceImpl(currencyDao); // конструктор с DAO
    }

    @Test
    void testCreateCurrency() {
        Currency currency = new Currency("840", "USD", "Dollar");
        service.createCurrency(currency);
        verify(currencyDao).add(currency);
    }

    @Test
    void testCreateCurrency_Null_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.createCurrency(null);
        });
    }

    @Test
    void testGetCurrencyById() {
        Currency currency = new Currency("978", "EUR", "Euro");
        currency.setId(1L);
        when(currencyDao.getById(1L)).thenReturn(Optional.of(currency));

        Optional<Currency> result = service.getCurrencyById(1L);

        assertTrue(result.isPresent());
        assertEquals("Euro", result.get().getCurrencyName());
    }

    @Test
    void testGetCurrencyById_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getCurrencyById(0L);
        });
    }

    @Test
    void testGetAllCurrencies() {
        List<Currency> list = List.of(
                new Currency("840", "USD", "Dollar"),
                new Currency("978", "EUR", "Euro")
        );
        when(currencyDao.getAll()).thenReturn(list);

        List<Currency> result = service.getAllCurrencies();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateCurrency() {
        Currency currency = new Currency("840", "USD", "Dollar");
        currency.setId(1L);

        service.updateCurrency(currency);

        verify(currencyDao).update(currency);
    }

    @Test
    void testDeleteCurrency() {
        service.deleteCurrency(1L);
        verify(currencyDao).deleteById(1L);
    }

    @Test
    void testDeleteCurrency_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteCurrency(0L);
        });
    }

    @Test
    void testClearAllCurrencies() {
        service.clearAllCurrencies();
        verify(currencyDao).clearTable();
    }

    @Test
    void testGetCurrencyByCode() {
        List<Currency> list = List.of(
                new Currency("840", "USD", "Dollar"),
                new Currency("978", "EUR", "Euro")
        );
        when(currencyDao.getAll()).thenReturn(list);

        Optional<Currency> result = service.getCurrencyByCode("usd");

        assertTrue(result.isPresent());
        assertEquals("Dollar", result.get().getCurrencyName());
    }

    @Test
    void testGetCurrencyByCode_Empty_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getCurrencyByCode("");
        });
    }
}


