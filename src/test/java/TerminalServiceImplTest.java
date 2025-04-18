import dao.Dao;
import model.MerchantCategoryCode;
import model.SalesPoint;
import model.Terminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.TerminalServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TerminalServiceImplTest {

    private Dao<Terminal> terminalDao;
    private TerminalServiceImpl terminalService;

    @BeforeEach
    public void setUp() {
        terminalDao = mock(Dao.class);
        terminalService = new TerminalServiceImpl(terminalDao);
    }

    private Terminal buildValidTerminal() {
        Terminal terminal = new Terminal();
        terminal.setTerminalId("123456789");

        MerchantCategoryCode mcc = new MerchantCategoryCode();
        mcc.setId(100L);
        terminal.setMccId(mcc);

        SalesPoint pos = new SalesPoint();
        pos.setId(200L);
        terminal.setPosId(pos);

        return terminal;
    }


    @Test
    public void testCreateTerminal() {
        Terminal terminal = buildValidTerminal();
        terminalService.createTerminal(terminal);
        verify(terminalDao).add(terminal);
    }

    @Test
    public void testGetTerminalById_Valid() {
        Terminal terminal = buildValidTerminal();
        when(terminalDao.getById(1L)).thenReturn(Optional.of(terminal));

        Optional<Terminal> result = terminalService.getTerminalById(1L);
        assertTrue(result.isPresent());
        assertEquals("123456789", result.get().getTerminalId());
    }

    @Test
    public void testGetTerminalById_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> terminalService.getTerminalById(null));
        assertThrows(IllegalArgumentException.class, () -> terminalService.getTerminalById(-10L));
    }

    @Test
    public void testDeleteTerminal_InvalidId_Throws() {
        assertThrows(IllegalArgumentException.class, () -> terminalService.deleteTerminal(0L));
        assertThrows(IllegalArgumentException.class, () -> terminalService.deleteTerminal(null));
    }

    @Test
    public void testGetTerminalsByPosId() {
        Terminal t1 = buildValidTerminal();
        Terminal t2 = buildValidTerminal();
        t2.getPosId().setId(201L);

        when(terminalDao.getAll()).thenReturn(Arrays.asList(t1, t2));

        List<Terminal> result = terminalService.getTerminalsByPosId(200L);
        assertEquals(1, result.size());
        assertEquals("123456789", result.get(0).getTerminalId());
    }


    @Test
    public void testGetTerminalsByMccId() {
        Terminal t1 = buildValidTerminal();
        Terminal t2 = buildValidTerminal();
        t2.getMccId().setId(101L);

        when(terminalDao.getAll()).thenReturn(Arrays.asList(t1, t2));

        List<Terminal> result = terminalService.getTerminalsByMccId(100L);
        assertEquals(1, result.size());
    }


    @Test
    public void testValidateTerminal_NullTerminal_Throws() {
        assertThrows(IllegalArgumentException.class, () -> terminalService.validateTerminal(null));
    }

    @Test
    public void testValidateTerminal_InvalidTerminalId_Throws() {
        Terminal terminal = buildValidTerminal();
        terminal.setTerminalId("1234");
        assertThrows(IllegalArgumentException.class, () -> terminalService.validateTerminal(terminal));
    }

    @Test
    public void testValidateTerminal_NullMccId_Throws() {
        Terminal terminal = buildValidTerminal();
        terminal.setMccId(null);
        assertThrows(IllegalArgumentException.class, () -> terminalService.validateTerminal(terminal));
    }

    @Test
    public void testValidateTerminal_NullPosId_Throws() {
        Terminal terminal = buildValidTerminal();
        terminal.setPosId(null);
        assertThrows(IllegalArgumentException.class, () -> terminalService.validateTerminal(terminal));
    }

    @Test
    public void testGetAllTerminals() {
        when(terminalDao.getAll()).thenReturn(Arrays.asList(buildValidTerminal()));
        List<Terminal> result = terminalService.getAllTerminals();
        assertEquals(1, result.size());
    }
}
