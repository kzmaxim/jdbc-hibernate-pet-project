import dao.Dao;
import dao.hibernate.CardStatusHibernateDaoImpl;
import model.*;
import model.utils.CardUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CardService;
import service.impl.CardServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import dao.Dao;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.CardServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceImplTest {

    private Dao<Card> mockCardDao;
    private Dao<CardStatus> mockCardStatusDao;
    private CardServiceImpl cardService;

    @BeforeEach
    void setUp() {
        mockCardDao = mock(Dao.class);
        mockCardStatusDao = mock(Dao.class);  // Инициализируем мок для CardStatus Dao
        cardService = new CardServiceImpl(mockCardDao, mockCardStatusDao);  // Передаем оба мока в конструктор CardServiceImpl
    }


    @Test
    void createCard_valid_callsAdd() {
        Card card = createValidCard();
        cardService.createCard(card);
        verify(mockCardDao).add(card);
    }

    @Test
    void createCard_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.createCard(null));
    }

    @Test
    void getCardById_valid_returnsCard() {
        Card card = createValidCard();
        when(mockCardDao.getById(1L)).thenReturn(Optional.of(card));
        Optional<Card> result = cardService.getCardById(1L);
        assertTrue(result.isPresent());
        assertEquals(card, result.get());
    }

    @Test
    void getCardById_invalidId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.getCardById(0L));
    }

    @Test
    void getAllCards_returnsList() {
        List<Card> cards = List.of(createValidCard(), createValidCard());
        when(mockCardDao.getAll()).thenReturn(cards);
        List<Card> result = cardService.getAllCards();
        assertEquals(2, result.size());
    }

    @Test
    void updateCard_valid_callsUpdate() {
        Card card = createValidCard();
        cardService.updateCard(card);
        verify(mockCardDao).update(card);
    }

    @Test
    void deleteCard_valid_callsDeleteById() {
        cardService.deleteCard(1L);
        verify(mockCardDao).deleteById(1L);
    }

    @Test
    void deleteCard_invalid_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.deleteCard(0L));
    }

    @Test
    void clearAllCards_callsClearTable() {
        cardService.clearAllCards();
        verify(mockCardDao).clearTable();
    }
    //@Disabled
    @Test
    void getCardsByAccountId_returnsFilteredCards() {
        Account account = new Account();
        account.setId(1L);

        Card card1 = createValidCard();
        card1.setAccountId(account);
        Card card2 = createValidCard();
        card2.setAccountId(account);

        when(mockCardDao.getAll()).thenReturn(List.of(card1, card2));

        List<Card> result = cardService.getCardsByAccountId(1L);
        assertEquals(2, result.size());
    }


    @Test
    void getCardsByAccountId_invalid_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> cardService.getCardsByAccountId(0L));
    }

    @Test
    void blockCard_valid_updatesStatus() {
        Card card = createValidCard();
        CardStatus newStatus = new CardStatus();
        newStatus.setId(2L);

        when(mockCardDao.getById(1L)).thenReturn(Optional.of(card));
        when(mockCardStatusDao.getById(2L)).thenReturn(Optional.of(newStatus));

        boolean result = cardService.blockCard(1L, 2L);
        assertTrue(result);
        assertEquals(2L, card.getCardStatusId().getId());
        verify(mockCardDao).update(card);
    }

    @Test
    void blockCard_cardNotFound_returnsFalse() {
        when(mockCardDao.getById(1L)).thenReturn(Optional.empty());
        boolean result = cardService.blockCard(1L, 2L);
        assertFalse(result);
    }

    @Test
    void blockCard_statusNotFound_returnsFalse() {
        Card card = createValidCard();
        when(mockCardDao.getById(1L)).thenReturn(Optional.of(card));
        when(mockCardStatusDao.getById(2L)).thenReturn(Optional.empty());

        boolean result = cardService.blockCard(1L, 2L);
        assertFalse(result);
    }

    private Card createValidCard() {
        Card card = new Card();
        card.setCardNumber("1234567812345678");
        card.setExpirationDate(LocalDate.of(2025, 12, 31));
        card.setHolderName("John Doe");

        CardStatus status = new CardStatus();
        status.setId(1L);
        card.setCardStatusId(status);

        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(1L);
        card.setPaymentSystemId(paymentSystem);

        Account account = new Account();
        account.setId(1L);
        card.setAccountId(account);

        return card;
    }
}
