package service.impl;

import main.java.dao.Dao;
import dao.jdbc.CurrencyJDBCDaoImpl;
import model.Currency;
import service.CurrencyService;

import java.util.List;
import java.util.Optional;

public class CurrencyServiceImpl implements CurrencyService {
    private final Dao<Currency> currencyDao;

    public CurrencyServiceImpl() {
        this.currencyDao = new CurrencyJDBCDaoImpl();
    }

    @Override
    public void createCurrency(Currency currency) {
        validateCurrency(currency);
        currencyDao.add(currency);
    }

    @Override
    public Optional<Currency> getCurrencyById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid currency ID");
        }
        return currencyDao.getById(id);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyDao.getAll();
    }

    @Override
    public void updateCurrency(Currency currency) {
        validateCurrency(currency);
        currencyDao.update(currency);
    }

    @Override
    public void deleteCurrency(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid currency ID");
        }
        currencyDao.deleteById(id);
    }

    @Override
    public void clearAllCurrencies() {
        currencyDao.clearTable();
    }

    @Override
    public Optional<Currency> getCurrencyByCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Currency code cannot be empty");
        }
        return currencyDao.getAll().stream()
                .filter(c -> code.equalsIgnoreCase(c.getCurrencyLetterCode()))
                .findFirst();
    }


    private void validateCurrency(Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        if (currency.getCurrencyDigitalCode() == null || currency.getCurrencyDigitalCode().isEmpty()) {
            throw new IllegalArgumentException("Digital code is required");
        }
        if (currency.getCurrencyLetterCode() == null || currency.getCurrencyLetterCode().isEmpty()) {
            throw new IllegalArgumentException("Letter code is required");
        }
        if (currency.getCurrencyName() == null || currency.getCurrencyName().isEmpty()) {
            throw new IllegalArgumentException("Currency name is required");
        }
    }
}
