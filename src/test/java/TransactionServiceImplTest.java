import dao.Dao;
import model.Terminal;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.impl.TransactionServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {
    private Dao<Transaction> transactionDao;
    private TransactionServiceImpl transactionService;

    @BeforeEach
    public void setUp() {
        transactionDao = Mockito.mock(Dao.class);
        transactionService = new TransactionServiceImpl(transactionDao);
    }

    private Transaction buildValidTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setTransactionName("Purchase");
        transaction.setSum(1000.0);

        Terminal terminal = new Terminal();
        terminal.setTerminalId("123456789");
        transaction.setTerminalId(terminal);

        return transaction;
    }

    @Test
    public void testCreateTransaction_Valid() {
        Transaction t = buildValidTransaction();
        transactionService.createTransaction(t);
        verify(transactionDao).add(t);
    }

    @Test
    public void testCreateTransaction_InvalidSum_Throws() {
        Transaction t = buildValidTransaction();
        t.setSum(0.0);
        assertThrows(IllegalArgumentException.class, () -> transactionService.createTransaction(t));
    }

    @Test
    public void testCreateTransaction_EmptyName_Throws() {
        Transaction t = buildValidTransaction();
        t.setTransactionName("");
        assertThrows(IllegalArgumentException.class, () -> transactionService.createTransaction(t));
    }

    @Test
    public void testGetTransactionById_Valid() {
        Transaction t = buildValidTransaction();
        when(transactionDao.getById(1L)).thenReturn(Optional.of(t));
        Optional<Transaction> result = transactionService.getTransactionById(1L);
        assertTrue(result.isPresent());
        assertEquals("Purchase", result.get().getTransactionName());
    }

    @Test
    public void testGetTransactionById_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.getTransactionById(0L));
    }

    @Test
    public void testGetAllTransactions() {
        List<Transaction> list = List.of(buildValidTransaction());
        when(transactionDao.getAll()).thenReturn(list);
        List<Transaction> result = transactionService.getAllTransactions();
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdateTransaction_Valid() {
        Transaction t = buildValidTransaction();
        transactionService.updateTransaction(t);
        verify(transactionDao).update(t);
    }

    @Test
    public void testDeleteTransaction_ValidId() {
        transactionService.deleteTransaction(1L);
        verify(transactionDao).deleteById(1L);
    }

    @Test
    public void testDeleteTransaction_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.deleteTransaction(0L));
    }

    @Test
    public void testClearAllTransactions() {
        transactionService.clearAllTransactions();
        verify(transactionDao).clearTable();
    }
}
