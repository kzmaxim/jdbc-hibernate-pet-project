import dao.Dao;
import model.ResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.impl.ResponseCodeServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResponseCodeServiceImplTest {

    private Dao<ResponseCode> responseCodeDao;
    private ResponseCodeServiceImpl responseCodeService;

    @BeforeEach
    public void setUp() {
        responseCodeDao = Mockito.mock(Dao.class);
        responseCodeService = new ResponseCodeServiceImpl(responseCodeDao);
    }

    @Test
    public void testCreateResponseCode() {
        ResponseCode rc = new ResponseCode("E001", "Error description", "HIGH");
        responseCodeService.createResponseCode(rc);
        verify(responseCodeDao).add(rc);
    }

    @Test
    public void testCreateResponseCode_Null_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            responseCodeService.createResponseCode(null);
        });
    }

    @Test
    public void testGetResponseCodeById_ValidId() {
        ResponseCode rc = new ResponseCode("E001", "Description", "LOW");
        when(responseCodeDao.getById(1L)).thenReturn(Optional.of(rc));

        Optional<ResponseCode> result = responseCodeService.getResponseCodeById(1L);
        assertTrue(result.isPresent());
        assertEquals("E001", result.get().getErrorCode());
    }

    @Test
    public void testGetResponseCodeById_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            responseCodeService.getResponseCodeById(0L);
        });
    }

    @Test
    public void testGetAllResponseCodes() {
        ResponseCode rc1 = new ResponseCode("E001", "Desc 1", "HIGH");
        ResponseCode rc2 = new ResponseCode("E002", "Desc 2", "LOW");
        when(responseCodeDao.getAll()).thenReturn(Arrays.asList(rc1, rc2));

        List<ResponseCode> result = responseCodeService.getAllResponseCodes();
        assertEquals(2, result.size());
    }

    @Test
    public void testUpdateResponseCode() {
        ResponseCode rc = new ResponseCode("E001", "Updated description", "MEDIUM");
        responseCodeService.updateResponseCode(rc);
        verify(responseCodeDao).update(rc);
    }

    @Test
    public void testDeleteResponseCode() {
        responseCodeService.deleteResponseCode(3L);
        verify(responseCodeDao).deleteById(3L);
    }

    @Test
    public void testDeleteResponseCode_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            responseCodeService.deleteResponseCode(null);
        });
    }

    @Test
    public void testClearAllResponseCodes() {
        responseCodeService.clearAllResponseCodes();
        verify(responseCodeDao).clearTable();
    }

    @Test
    public void testGetResponseCodeByErrorCode_Found() {
        ResponseCode rc = new ResponseCode("E001", "Some error", "LOW");
        when(responseCodeDao.getAll()).thenReturn(List.of(rc));

        Optional<ResponseCode> result = responseCodeService.getResponseCodeByErrorCode("E001");
        assertTrue(result.isPresent());
    }

    @Test
    public void testGetResponseCodeByErrorCode_Empty_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            responseCodeService.getResponseCodeByErrorCode("");
        });
    }

    @Test
    public void testGetResponseCodesByLevel_Match() {
        ResponseCode rc1 = new ResponseCode("E001", "Description", "HIGH");
        ResponseCode rc2 = new ResponseCode("E002", "Another", "LOW");
        when(responseCodeDao.getAll()).thenReturn(List.of(rc1, rc2));

        List<ResponseCode> result = responseCodeService.getResponseCodesByLevel("LOW");
        assertEquals(1, result.size());
        assertEquals("E002", result.get(0).getErrorCode());
    }

    @Test
    public void testGetResponseCodesByLevel_Empty_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            responseCodeService.getResponseCodesByLevel(null);
        });
    }
}

