package service.impl;

import dao.Dao;
import dao.hibernate.CardHibernateDaoImpl;
import dao.hibernate.CardStatusHibernateDaoImpl;
import dao.jdbc.CardJDBCDaoImpl;
import model.Card;
import model.CardStatus;
import service.CardService;

import java.util.List;
import java.util.Optional;

public class CardServiceImpl implements CardService {
    private final Dao<Card> cardDao;
    private final Dao<CardStatus> cardStatusDao;

    public CardServiceImpl(Dao<Card> cardDao, Dao<CardStatus> cardStatusDao) {
        this.cardDao = cardDao;
        this.cardStatusDao = cardStatusDao;
    }
    public CardServiceImpl() {
        this.cardDao = new CardHibernateDaoImpl();
        this.cardStatusDao = new CardStatusHibernateDaoImpl();
    }


    @Override
    public void createCard(Card card) {
        validateCard(card);
        cardDao.add(card);
    }

    @Override
    public Optional<Card> getCardById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid card ID");
        }
        return cardDao.getById(id);
    }

    @Override
    public List<Card> getAllCards() {
        return cardDao.getAll();
    }

    @Override
    public void updateCard(Card card) {
        validateCard(card);
        cardDao.update(card);
    }

    @Override
    public void deleteCard(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid card ID");
        }
        cardDao.deleteById(id);
    }

    @Override
    public void clearAllCards() {
        cardDao.clearTable();
    }

    @Override
    public List<Card> getCardsByAccountId(Long accountId) {
        if (accountId == null || accountId <= 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        List<Card> allCards = cardDao.getAll();
        return allCards.stream()
                .filter(card -> card.getAccountId().getId().equals(accountId))
                .toList();
    }



    @Override
    public boolean blockCard(Long cardId, Long statusId) {
        Optional<Card> cardOpt = cardDao.getById(cardId);
        if (cardOpt.isEmpty()) {
            return false;
        }

        Optional<CardStatus> statusOpt = cardStatusDao.getById(statusId);
        if (statusOpt.isEmpty()) {
            return false;
        }

        Card card = cardOpt.get();
        card.setCardStatusId(statusOpt.get());
        cardDao.update(card);
        return true;
    }


    private void validateCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }
        if (card.getCardNumber() == null || card.getCardNumber().isEmpty()) {
            throw new IllegalArgumentException("Card number is required");
        }
        if (card.getExpirationDate() == null) {
            throw new IllegalArgumentException("Expiration date is required");
        }
        if (card.getHolderName() == null || card.getHolderName().isEmpty()) {
            throw new IllegalArgumentException("Holder name is required");
        }
        if (card.getCardStatusId() == null || card.getCardStatusId().getId() == null) {
            throw new IllegalArgumentException("Invalid card status ID");
        }
        if (card.getPaymentSystemId() == null || card.getPaymentSystemId().getId() == null) {
            throw new IllegalArgumentException("Invalid payment system ID");
        }
        if (card.getAccountId() == null || card.getAccountId().getId() == null) {
            throw new IllegalArgumentException("Invalid account ID");
        }
    }
}