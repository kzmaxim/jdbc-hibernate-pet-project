package service;

import model.IssuingBank;

import java.util.List;
import java.util.Optional;

public interface IssuingBankService {
    void createIssuingBank(IssuingBank issuingBank);
    Optional<IssuingBank> getIssuingBankById(Long id);
    List<IssuingBank> getAllIssuingBanks();
    void updateIssuingBank(IssuingBank issuingBank);
    void deleteIssuingBank(Long id);
    void clearAllIssuingBanks();
    Optional<IssuingBank> getIssuingBankByBic(String bic);
    Optional<IssuingBank> getIssuingBankByBin(String bin);
}
