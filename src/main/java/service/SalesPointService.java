package service;

import model.SalesPoint;

import java.util.List;
import java.util.Optional;

public interface SalesPointService {
    void createSalesPoint(SalesPoint salesPoint);
    Optional<SalesPoint> getSalesPointById(Long id);
    List<SalesPoint> getAllSalesPoints();
    void updateSalesPoint(SalesPoint salesPoint);
    void deleteSalesPoint(Long id);
    void clearAllSalesPoints();
    List<SalesPoint> getSalesPointsByBankId(Long bankId);
    boolean validateSalesPoint(SalesPoint salesPoint);
}
