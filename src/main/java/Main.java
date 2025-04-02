package main.java;

import main.java.dao.jdbc.CardStatusJDBCDaoImpl;
import main.java.dao.jdbc.PaymentSystemJDBCDaoImpl;
import main.java.model.CardStatus;
import main.java.model.PaymentSystem;
import main.java.model.Transaction;
import main.java.service.impl.CardStatusServiceImpl;
import main.java.service.impl.PaymentSystemServiceImpl;
import main.java.service.impl.TransactionServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        CardStatusServiceImpl cardStatusService = new CardStatusServiceImpl();
//        CardStatus cardStatus = new CardStatus(2L, "Test2");
//        cardStatusService.createCardStatus(cardStatus);
//
//        List<CardStatus> allCardStatuses = cardStatusService.getAllCardStatuses();
//        for (CardStatus allCardStatus : allCardStatuses) {
//            System.out.println(allCardStatus);
//        }
//        PaymentSystemServiceImpl paymentSystemService = new PaymentSystemServiceImpl();
//        PaymentSystem paymentSystem = new PaymentSystem(1L, "Payment System 1");
//        paymentSystemService.createPaymentSystem(paymentSystem);
//
//        List<PaymentSystem> allPaymentSystems = paymentSystemService.getAllPaymentSystems();
//        for (PaymentSystem allPaymentSystem : allPaymentSystems) {
//            System.out.println(allPaymentSystem);
//        }
//        CardStatusJDBCDaoImpl cardStatusJDBCDao = new CardStatusJDBCDaoImpl();
//        cardStatusJDBCDao.dropTable();
//        PaymentSystemJDBCDaoImpl paymentSystemJDBCDao = new PaymentSystemJDBCDaoImpl();
//        paymentSystemJDBCDao.dropTable();
//
//        cardStatusJDBCDao.createTable();
//        paymentSystemJDBCDao.createTable();
        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        List<Transaction> allTransactions = transactionService.getAllTransactions();
        for (Transaction allTransaction : allTransactions) {
            System.out.println(allTransaction);
        }
    }
}
