//package java;

import configuration.HibernateUtil;
import dao.jdbc.CardStatusJDBCDaoImpl;
import dao.jdbc.PaymentSystemJDBCDaoImpl;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import service.AccountService;
import service.CardService;
import service.impl.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        // Добавляем связанные сущности
        CardStatus status1 = new CardStatus("Card is valid");
        PaymentSystem ps1 = new PaymentSystem("VISA");
        Currency currency = new Currency("840", "USD", "US Dollar");
        IssuingBank bank = new IssuingBank("041234569", "12345", "Test Bank");

        Account acc1 = new Account("408178100000000001", 10000.0, currency, bank);
        Account acc2 = new Account("408178100000000002", 5000.0, currency, bank);

        session.persist(status1);
        System.out.println("Добавлен статус в card_status: " + status1);

        session.persist(ps1);
        System.out.println("Добавлена платёжная система: " + ps1);

        session.persist(currency);
        System.out.println("Добавлена валюта: " + currency);

        session.persist(bank);
        System.out.println("Добавлен банк-эмитент: " + bank);

        session.persist(acc1);
        session.persist(acc2);
        System.out.println("Добавлены аккаунты: " + acc1 + ", " + acc2);

        // Добавляем карты
        Card card1 = new Card("4123450000000019", LocalDate.of(2026, 12, 31), "Ivan Ivanov", status1, ps1, acc1, Timestamp.valueOf("2026-12-31 15:30:45.123"), Timestamp.valueOf("2026-12-31 15:32:12.123"));
        Card card2 = new Card("4123450000000020", LocalDate.of(2026, 11, 30), "Petr Petrov", status1, ps1, acc1, Timestamp.valueOf("2026-11-30 15:30:45.123"), Timestamp.valueOf("2026-11-30 15:32:12.123"));
        Card card3 = new Card("4123450000000021", LocalDate.of(2025, 10, 30), "Anna Smirnova", status1, ps1, acc2, Timestamp.valueOf("2025-10-30 15:30:45.123"), Timestamp.valueOf("2025-10-30 15:32:12.123"));
        Card card4 = new Card("4123450000000022", LocalDate.of(2025, 9, 30), "Olga Ivanova", status1, ps1, acc2, Timestamp.valueOf("2026-09-30 15:30:45.123"), Timestamp.valueOf("2025-09-30 15:32:12.123"));

        session.persist(card1);
        System.out.println("Добавлена карта: " + card1);

        session.persist(card2);
        System.out.println("Добавлена карта: " + card2);

        session.persist(card3);
        System.out.println("Добавлена карта: " + card3);

        session.persist(card4);
        System.out.println("Добавлена карта: " + card4);

        tx.commit();

        // Получение всех карт
        session.clear(); // очищаем контекст
        List<Card> allCards = session.createQuery("FROM Card", Card.class).list();
        System.out.println("\nСписок всех карт:");
        allCards.forEach(System.out::println);

        // Обновление 2-х карт
        tx = session.beginTransaction();
        card1.setHolderName("Ivan I. Ivanov");
        card2.setHolderName("Petr P. Petrov");
        session.merge(card1);
        session.merge(card2);
        tx.commit();

        System.out.println("\nОбновлённые карты:");
        System.out.println(card1);
        System.out.println(card2);

        session.close();
        sessionFactory.close();
    }
}
