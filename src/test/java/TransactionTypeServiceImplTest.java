import dao.Dao;
import model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.impl.TransactionTypeServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionTypeServiceImplTest {
    private Dao<TransactionType> transactionTypeDao;
    private TransactionTypeServiceImpl service;

    @BeforeEach
    public void setUp() {
        transactionTypeDao = Mockito.mock(Dao.class);
        service = new TransactionTypeServiceImpl(transactionTypeDao);
    }

    private TransactionType buildValidType(String name, String operator) {
        TransactionType type = new TransactionType();
        type.setId(1L);
        type.setTransactionTypeName(name);
        type.setOperator(operator);
        return type;
    }

    @Test
    public void testCreateTransactionType_Valid() {
        TransactionType type = buildValidType("Income", "+");
        service.createTransactionType(type);
        verify(transactionTypeDao).add(type);
    }

    @Test
    public void testCreateTransactionType_InvalidName_Throws() {
        TransactionType type = buildValidType("", "+");
        assertThrows(IllegalArgumentException.class, () -> service.createTransactionType(type));
    }

    @Test
    public void testCreateTransactionType_InvalidOperator_Throws() {
        TransactionType type = buildValidType("Test", "*");
        assertThrows(IllegalArgumentException.class, () -> service.createTransactionType(type));
    }

    @Test
    public void testGetTransactionTypeById_Valid() {
        TransactionType type = buildValidType("Income", "+");
        when(transactionTypeDao.getById(1L)).thenReturn(Optional.of(type));
        Optional<TransactionType> result = service.getTransactionTypeById(1L);
        assertTrue(result.isPresent());
        assertEquals("Income", result.get().getTransactionTypeName());
    }

    @Test
    public void testGetTransactionTypeById_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> service.getTransactionTypeById(0L));
    }

    @Test
    public void testGetAllTransactionTypes() {
        List<TransactionType> types = List.of(buildValidType("Income", "+"));
        when(transactionTypeDao.getAll()).thenReturn(types);
        List<TransactionType> result = service.getAllTransactionTypes();
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdateTransactionType_Valid() {
        TransactionType type = buildValidType("Expense", "-");
        service.updateTransactionType(type);
        verify(transactionTypeDao).update(type);
    }

    @Test
    public void testDeleteTransactionType_ValidId() {
        service.deleteTransactionType(1L);
        verify(transactionTypeDao).deleteById(1L);
    }

    @Test
    public void testDeleteTransactionType_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteTransactionType(-10L));
    }

    @Test
    public void testClearAllTransactionTypes() {
        service.clearAllTransactionTypes();
        verify(transactionTypeDao).clearTable();
    }

    @Test
    public void testGetTransactionTypeByName_Found() {
        TransactionType type = buildValidType("Income", "+");
        when(transactionTypeDao.getAll()).thenReturn(List.of(type));
        Optional<TransactionType> result = service.getTransactionTypeByName("Income");
        assertTrue(result.isPresent());
        assertEquals("+", result.get().getOperator());
    }

    @Test
    public void testGetTransactionTypeByName_Empty_Throws() {
        assertThrows(IllegalArgumentException.class, () -> service.getTransactionTypeByName(""));
    }

    @Test
    public void testGetTransactionTypesByOperator() {
        TransactionType t1 = buildValidType("Income", "+");
        TransactionType t2 = buildValidType("Bonus", "+");
        when(transactionTypeDao.getAll()).thenReturn(List.of(t1, t2));
        List<TransactionType> result = service.getTransactionTypesByOperator("+");
        assertEquals(2, result.size());
    }

    @Test
    public void testGetTransactionTypesByOperator_Invalid_Throws() {
        assertThrows(IllegalArgumentException.class, () -> service.getTransactionTypesByOperator(""));
    }

    @Test
    public void testValidateTransactionType_Valid() {
        TransactionType type = buildValidType("Bonus", "+");
        assertTrue(service.validateTransactionType(type));
    }

    @Test
    public void testValidateTransactionType_Null_Throws() {
        assertThrows(IllegalArgumentException.class, () -> service.validateTransactionType(null));
    }

    @Test
    public void testValidateTransactionType_InvalidOperator_Throws() {
        TransactionType type = buildValidType("Invalid", "*");
        assertThrows(IllegalArgumentException.class, () -> service.validateTransactionType(type));
    }
}

