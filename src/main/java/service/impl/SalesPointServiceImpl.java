package service.impl;

import main.java.dao.Dao;
import dao.jdbc.SalesPointJDBCDaoImpl;
import model.SalesPoint;
import service.SalesPointService;

import java.util.List;
import java.util.Optional;

public class SalesPointServiceImpl implements SalesPointService {
    private final Dao<SalesPoint> salesPointDao;

    public SalesPointServiceImpl() {
        this.salesPointDao = new SalesPointJDBCDaoImpl();
    }

    @Override
    public void createSalesPoint(SalesPoint salesPoint) {
        validateSalesPoint(salesPoint);
        salesPointDao.add(salesPoint);
    }

    @Override
    public Optional<SalesPoint> getSalesPointById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid sales point ID");
        }
        return salesPointDao.getById(id);
    }

    @Override
    public List<SalesPoint> getAllSalesPoints() {
        return salesPointDao.getAll();
    }

    @Override
    public void updateSalesPoint(SalesPoint salesPoint) {
        validateSalesPoint(salesPoint);
        salesPointDao.update(salesPoint);
    }

    @Override
    public void deleteSalesPoint(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid sales point ID");
        }
        salesPointDao.deleteById(id);
    }

    @Override
    public void clearAllSalesPoints() {
        salesPointDao.clearTable();
    }

    @Override
    public List<SalesPoint> getSalesPointsByBankId(Long bankId) {
        if (bankId == null || bankId <= 0) {
            throw new IllegalArgumentException("Invalid bank ID");
        }
        return salesPointDao.getAll().stream()
                .filter(sp -> bankId.equals(sp.getAcquiringBankId()))
                .toList();
    }

    @Override
    public boolean validateSalesPoint(SalesPoint salesPoint) {
        if (salesPoint == null) {
            throw new IllegalArgumentException("Sales point cannot be null");
        }
        if (salesPoint.getPosName() == null || salesPoint.getPosName().isEmpty()) {
            throw new IllegalArgumentException("Point name is required");
        }
        if (salesPoint.getPosAddress() == null || salesPoint.getPosAddress().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (salesPoint.getPosInn() == null || salesPoint.getPosInn().isEmpty()) {
            throw new IllegalArgumentException("INN is required");
        }
        if (salesPoint.getAcquiringBankId() == null || salesPoint.getAcquiringBankId() <= 0) {
            throw new IllegalArgumentException("Valid acquiring bank ID is required");
        }

        // Дополнительная валидация ИНН (10 или 12 цифр)
        String inn = salesPoint.getPosInn();
        if (!inn.matches("^\\d{10}$|^\\d{12}$")) {
            throw new IllegalArgumentException("INN must be 10 or 12 digits");
        }

        return true;
    }
}
