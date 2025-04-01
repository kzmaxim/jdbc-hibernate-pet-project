package main.java.service.impl;

import main.java.dao.Dao;
import main.java.dao.jdbc.CardStatusJDBCDaoImpl;
import main.java.model.CardStatus;
import main.java.service.CardStatusService;

import java.util.List;
import java.util.Optional;

public class CardStatusServiceImpl implements CardStatusService {
    private final Dao<CardStatus> cardStatusDao;

    public CardStatusServiceImpl() {
        this.cardStatusDao = new CardStatusJDBCDaoImpl();
    }

    @Override
    public void createCardStatus(CardStatus cardStatus) {
        validateCardStatus(cardStatus);
        cardStatusDao.add(cardStatus);
    }

    @Override
    public Optional<CardStatus> getCardStatusById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid card status ID");
        }
        return cardStatusDao.getById(id);
    }

    @Override
    public List<CardStatus> getAllCardStatuses() {
        return cardStatusDao.getAll();
    }

    @Override
    public void updateCardStatus(CardStatus cardStatus) {
        validateCardStatus(cardStatus);
        cardStatusDao.update(cardStatus);
    }

    @Override
    public void deleteCardStatus(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid card status ID");
        }
        cardStatusDao.deleteById(id);
    }

    @Override
    public void clearAllCardStatuses() {
        cardStatusDao.clearTable();
    }

    private void validateCardStatus(CardStatus cardStatus) {
        if (cardStatus == null) {
            throw new IllegalArgumentException("Card status cannot be null");
        }
        if (cardStatus.getCardStatusName() == null || cardStatus.getCardStatusName().isEmpty()) {
            throw new IllegalArgumentException("Card status name is required");
        }
    }
}