package service;

import model.PaymentSystem;

import java.util.List;
import java.util.Optional;

public interface PaymentSystemService {
    void createPaymentSystem(PaymentSystem paymentSystem);
    Optional<PaymentSystem> getPaymentSystemById(Long id);
    List<PaymentSystem> getAllPaymentSystems();
    void updatePaymentSystem(PaymentSystem paymentSystem);
    void deletePaymentSystem(Long id);
    void clearAllPaymentSystems();
    Optional<PaymentSystem> getPaymentSystemByName(String name);
}
