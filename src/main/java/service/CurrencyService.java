package service;

import model.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {
    void createCurrency(Currency currency);
    Optional<Currency> getCurrencyById(Long id);
    List<Currency> getAllCurrencies();
    void updateCurrency(Currency currency);
    void deleteCurrency(Long id);
    void clearAllCurrencies();
    Optional<Currency> getCurrencyByCode(String code);
}
