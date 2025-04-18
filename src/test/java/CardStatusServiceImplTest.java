import dao.Dao;
import model.CardStatus;
import org.junit.jupiter.api.*;
import service.CardStatusService;
import service.impl.CardStatusServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CardStatusServiceImplTest {
    private CardStatusServiceImpl service;
    private TestCardStatusDao testDao;

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String VALID_NAME = "ACTIVE";
    private static final String EMPTY_NAME = "";

    @BeforeEach
    void setUp() {
        testDao = new TestCardStatusDao();
        service = new CardStatusServiceImpl(testDao);
    }

    @AfterEach
    void tearDown() {
        service.clearAllCardStatuses();
    }

    private CardStatus createTestCardStatus(String name) {
        CardStatus status = new CardStatus();
        status.setCardStatusName(name);
        return status;
    }

    @Test
    @DisplayName("Создание валидного статуса карты")
    void createCardStatus_WithValidData_ShouldAddToDao() {
        CardStatus status = createTestCardStatus(VALID_NAME);
        service.createCardStatus(status);

        assertEquals(1, testDao.getAll().size());
        assertEquals(VALID_NAME, testDao.getAll().get(0).getCardStatusName());
    }

    @Test
    @DisplayName("Создание статуса карты с null объектом")
    void createCardStatus_WithNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createCardStatus(null));
    }

    @Test
    @DisplayName("Создание статуса карты с пустым именем")
    void createCardStatus_WithEmptyName_ShouldThrowException() {
        CardStatus status = createTestCardStatus(EMPTY_NAME);
        assertThrows(IllegalArgumentException.class,
                () -> service.createCardStatus(status));
    }

    @Test
    @DisplayName("Получение существующего статуса по ID")
    void getCardStatusById_WithExistingId_ShouldReturnStatus() {
        CardStatus status = createTestCardStatus(VALID_NAME);
        testDao.add(status);
        Long generatedId = testDao.getAll().get(0).getId();

        Optional<CardStatus> result = service.getCardStatusById(generatedId);
        assertTrue(result.isPresent());
        assertEquals(VALID_NAME, result.get().getCardStatusName());
    }

    @Test
    @DisplayName("Получение несуществующего статуса по ID")
    void getCardStatusById_WithNonExistingId_ShouldReturnEmpty() {
        Optional<CardStatus> result = service.getCardStatusById(999L);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Получение статуса по невалидному ID")
    void getCardStatusById_WithInvalidId_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getCardStatusById(INVALID_ID));
        assertThrows(IllegalArgumentException.class,
                () -> service.getCardStatusById(null));
    }

    @Test
    @DisplayName("Получение всех статусов карт")
    void getAllCardStatuses_ShouldReturnAllStatuses() {
        testDao.add(createTestCardStatus("ACTIVE"));
        testDao.add(createTestCardStatus("BLOCKED"));

        List<CardStatus> result = service.getAllCardStatuses();
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Обновление существующего статуса карты")
    void updateCardStatus_WithExistingStatus_ShouldUpdate() {
        CardStatus status = createTestCardStatus("OLD_NAME");
        testDao.add(status);
        Long generatedId = testDao.getAll().get(0).getId();

        CardStatus toUpdate = testDao.getById(generatedId).get();
        toUpdate.setCardStatusName("NEW_NAME");
        service.updateCardStatus(toUpdate);

        assertEquals("NEW_NAME", testDao.getById(generatedId).get().getCardStatusName());
    }

    @Test
    @DisplayName("Удаление существующего статуса карты")
    void deleteCardStatus_WithExistingId_ShouldRemove() {
        CardStatus status = createTestCardStatus(VALID_NAME);
        testDao.add(status);
        Long generatedId = testDao.getAll().get(0).getId();

        service.deleteCardStatus(generatedId);
        assertFalse(testDao.getById(generatedId).isPresent());
    }

    @Test
    @DisplayName("Очистка всех статусов карт")
    void clearAllCardStatuses_ShouldRemoveAll() {
        testDao.add(createTestCardStatus("STATUS1"));
        testDao.add(createTestCardStatus("STATUS2"));

        service.clearAllCardStatuses();
        assertTrue(service.getAllCardStatuses().isEmpty());
    }

    private static class TestCardStatusDao implements Dao<CardStatus> {
        private final Map<Long, CardStatus> statuses = new HashMap<>();
        private long idCounter = 1;

        @Override
        public void createTable() {}

        @Override
        public void dropTable() {}

        @Override
        public int add(CardStatus cardStatus) {
            cardStatus.setId(idCounter++);
            statuses.put(cardStatus.getId(), cardStatus);
            return 1;
        }

        @Override
        public int deleteById(Long id) {
            statuses.remove(id);
            return 1;
        }

        @Override
        public Optional<CardStatus> getById(Long id) {
            return Optional.ofNullable(statuses.get(id));
        }

        @Override
        public List<CardStatus> getAll() {
            return new ArrayList<>(statuses.values());
        }

        @Override
        public void update(CardStatus cardStatus) {
            statuses.put(cardStatus.getId(), cardStatus);
        }

        @Override
        public void clearTable() {
            statuses.clear();
        }
    }
}
