package main.java.service;

import main.java.model.AcquiringBank;

import java.util.List;
import java.util.Optional;

public interface AcquiringBankService {
    void createAcquiringBank(AcquiringBank acquiringBank);
    Optional<AcquiringBank> getAcquiringBankById(Long id);
    List<AcquiringBank> getAllAcquiringBanks();
    void updateAcquiringBank(AcquiringBank acquiringBank);
    void deleteAcquiringBank(Long id);
    void clearAllAcquiringBanks();
}
