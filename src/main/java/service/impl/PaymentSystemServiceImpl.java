package service.impl;

import dao.Dao;
import dao.hibernate.PaymentSystemHibernateDaoImpl;
import dao.jdbc.PaymentSystemJDBCDaoImpl;
import model.PaymentSystem;
import service.PaymentSystemService;

import java.util.List;
import java.util.Optional;

public class PaymentSystemServiceImpl implements PaymentSystemService {
    private final Dao<PaymentSystem> paymentSystemDao;

    public PaymentSystemServiceImpl() {
        this.paymentSystemDao = new PaymentSystemHibernateDaoImpl();
    }

    @Override
    public void createPaymentSystem(PaymentSystem paymentSystem) {
        validatePaymentSystem(paymentSystem);
        paymentSystemDao.add(paymentSystem);
    }

    @Override
    public Optional<PaymentSystem> getPaymentSystemById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid payment system ID");
        }
        return paymentSystemDao.getById(id);
    }

    @Override
    public List<PaymentSystem> getAllPaymentSystems() {
        return paymentSystemDao.getAll();
    }

    @Override
    public void updatePaymentSystem(PaymentSystem paymentSystem) {
        validatePaymentSystem(paymentSystem);
        paymentSystemDao.update(paymentSystem);
    }

    @Override
    public void deletePaymentSystem(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid payment system ID");
        }
        paymentSystemDao.deleteById(id);
    }

    @Override
    public void clearAllPaymentSystems() {
        paymentSystemDao.clearTable();
    }

    @Override
    public Optional<PaymentSystem> getPaymentSystemByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Payment system name cannot be empty");
        }
        return paymentSystemDao.getAll().stream()
                .filter(ps -> name.equalsIgnoreCase(ps.getPaymentSystemName()))
                .findFirst();
    }

    private void validatePaymentSystem(PaymentSystem paymentSystem) {
        if (paymentSystem == null) {
            throw new IllegalArgumentException("Payment system cannot be null");
        }
        if (paymentSystem.getPaymentSystemName() == null ||
                paymentSystem.getPaymentSystemName().isEmpty()) {
            throw new IllegalArgumentException("Payment system name is required");
        }
    }
}
