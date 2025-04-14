package service;

import model.CardStatus;

import java.util.List;
import java.util.Optional;

public interface CardStatusService {
    void createCardStatus(CardStatus cardStatus);
    Optional<CardStatus> getCardStatusById(Long id);
    List<CardStatus> getAllCardStatuses();
    void updateCardStatus(CardStatus cardStatus);
    void deleteCardStatus(Long id);
    void clearAllCardStatuses();
}
