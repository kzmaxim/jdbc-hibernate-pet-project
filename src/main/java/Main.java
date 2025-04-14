package main.java;

import dao.jdbc.CardStatusJDBCDaoImpl;
import dao.jdbc.PaymentSystemJDBCDaoImpl;
import model.*;
import service.AccountService;
import service.CardService;
import service.impl.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создание 4 объектов Card и добавление в таблицу

//        CardServiceImpl cardService = new CardServiceImpl();
//        AccountService accountService = new AccountServiceImpl();
//        accountService.createAccount(new Account(5L, "7309330274148589", 1000.0, 4L, 1L));
//        accountService.createAccount(new Account(6L, "40817810500000000005", 1500.0, 5L, 2L));
//        accountService.createAccount(new Account(7L, "40817810500000000005", 1300.0, 6L, 3L));
//        accountService.createAccount(new Account(8L, "40817810600000000006", 2400.0, 7L, 2L));
//
//        Card card1 = new Card(
//                5L,
//                "7309330274148589",
//                LocalDate.of(2025, 2, 12),
//                "IVANOV I. IVANOVICH",
//                2L,
//                3L,
//                6L,
//                Timestamp.valueOf("2023-09-23 12:15:06.175"),
//                Timestamp.valueOf("2023-09-23 12:16:08.256")
//        );
//        Card card2 = new Card(
//                6L,
//                "9374830274148589",
//                LocalDate.of(2025, 2, 12),
//                "SMIRNOV D. DMITRIEVICH",
//                3L,
//                4L,
//                7L,
//                Timestamp.valueOf("2023-09-23 12:15:06.175"),
//                Timestamp.valueOf("2023-09-23 12:16:08.256")
//        );
//        Card card3 = new Card(
//                7L,
//                "7309330274148589",
//                LocalDate.of(2025, 2, 12),
//                "ALEXEEV D. ALEXEEVICH",
//                4L,
//                5L,
//                8L,
//                Timestamp.valueOf("2023-09-23 12:15:06.175"),
//                Timestamp.valueOf("2023-09-23 12:16:08.256")
//        );
//        Card card4 = new Card(
//                8L,
//                "6100430274148589",
//                LocalDate.of(2025, 2, 12),
//                "BONDAREV B. BORISOVICH",
//                5L,
//                6L,
//                10L,
//                Timestamp.valueOf("2023-09-23 12:15:06.175"),
//                Timestamp.valueOf("2023-09-23 12:16:08.256")
//        );
//        cardService.createCard(card1);
//        cardService.createCard(card2);
//        cardService.createCard(card3);
//        cardService.createCard(card4);


        // Получение всех карточек
//        CardService cardService = new CardServiceImpl();
//        List<Card> allCards = cardService.getAllCards();
//        for (Card card : allCards) {
//            System.out.println(card);
//        }

        // Обновление 2-х объектов
        CardService cardService = new CardServiceImpl();
        Card updateCard = cardService.getCardById(7L).get();
        updateCard.setCardNumber(updateCard.getCardNumber() + 2849);
        cardService.updateCard(updateCard);
    }
}
