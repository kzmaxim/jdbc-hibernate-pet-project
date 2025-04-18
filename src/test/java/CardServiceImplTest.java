import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CardService;
import service.impl.CardServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardServiceImplTest {
    private CardService service;
    private static final String CARD_NUMBER = "123456789";
    private static final LocalDate EXPIRATION_DATE = LocalDate.of(2026, 12, 31);
    private static final String HOLDER_NAME = "John";
    private static final CardStatus CARD_STATUS = new CardStatus("Card is valid");
    private static final PaymentSystem PAYMENT_SYSTEM = new PaymentSystem("VISA");
    private static final Account ACCOUNT =  new Account("408178100000000001", 10000.0, new Currency("840", "USD", "US Dollar"), new IssuingBank("041234569", "12345", "Test Bank"));
    private static final Timestamp RECEIVED_FROM_ISSUING_BANK = Timestamp.valueOf("2026-12-31 15:30:45.123");
    private static final Timestamp SENT_TO_ISSUING_BANK = Timestamp.valueOf("2026-12-31 15:30:45.123");


    @BeforeEach
    void setUp() {
        service = new CardServiceImpl();
        service.clearAllCards();
    }
    @AfterEach
    void tearDown() {
        service.clearAllCards();
    }

    @Test
    void createCard() {
        Card card = new Card(CARD_NUMBER, EXPIRATION_DATE, HOLDER_NAME, CARD_STATUS, PAYMENT_SYSTEM, ACCOUNT, RECEIVED_FROM_ISSUING_BANK, SENT_TO_ISSUING_BANK);
        service.createCard(card);

        List<Card> cards = service.getAllCards();
        assertEquals(1, cards.size());
        assertEquals(CARD_NUMBER, cards.get(0).getCardNumber());
        assertEquals(EXPIRATION_DATE, cards.get(0).getExpirationDate());
        assertEquals(HOLDER_NAME, cards.get(0).getHolderName());
    }
}

