package service;

import model.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    void createCard(Card card);
    Optional<Card> getCardById(Long id);
    List<Card> getAllCards();
    void updateCard(Card card);
    void deleteCard(Long id);
    void clearAllCards();
    List<Card> getCardsByAccountId(Long accountId);
    boolean blockCard(Long cardId, Long statusId);
}
