package service.impl;

import dao.Dao;
import dao.hibernate.IssuingBankHibernateDaoImpl;
import dao.jdbc.IssuingBankJDBCDaoImpl;
import model.IssuingBank;
import service.IssuingBankService;

import java.util.List;
import java.util.Optional;

public class IssuingBankServiceImpl implements IssuingBankService {
    private final Dao<IssuingBank> issuingBankDao;

    public IssuingBankServiceImpl() {
        this.issuingBankDao = new IssuingBankHibernateDaoImpl();
    }

    @Override
    public void createIssuingBank(IssuingBank issuingBank) {
        validateIssuingBank(issuingBank);
        issuingBankDao.add(issuingBank);
    }

    @Override
    public Optional<IssuingBank> getIssuingBankById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid issuing bank ID");
        }
        return issuingBankDao.getById(id);
    }

    @Override
    public List<IssuingBank> getAllIssuingBanks() {
        return issuingBankDao.getAll();
    }

    @Override
    public void updateIssuingBank(IssuingBank issuingBank) {
        validateIssuingBank(issuingBank);
        issuingBankDao.update(issuingBank);
    }

    @Override
    public void deleteIssuingBank(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid issuing bank ID");
        }
        issuingBankDao.deleteById(id);
    }

    @Override
    public void clearAllIssuingBanks() {
        issuingBankDao.clearTable();
    }

    @Override
    public Optional<IssuingBank> getIssuingBankByBic(String bic) {
        if (bic == null || bic.isEmpty()) {
            throw new IllegalArgumentException("BIC cannot be empty");
        }
        return issuingBankDao.getAll().stream()
                .filter(bank -> bic.equals(bank.getBic()))
                .findFirst();
    }

    @Override
    public Optional<IssuingBank> getIssuingBankByBin(String bin) {
        if (bin == null || bin.isEmpty()) {
            throw new IllegalArgumentException("BIN cannot be empty");
        }
        return issuingBankDao.getAll().stream()
                .filter(bank -> bin.equals(bank.getBin()))
                .findFirst();
    }

    private void validateIssuingBank(IssuingBank issuingBank) {
        if (issuingBank == null) {
            throw new IllegalArgumentException("Issuing bank cannot be null");
        }
        if (issuingBank.getBic() == null || issuingBank.getBic().isEmpty()) {
            throw new IllegalArgumentException("BIC is required");
        }
        if (issuingBank.getBin() == null || issuingBank.getBin().isEmpty()) {
            throw new IllegalArgumentException("BIN is required");
        }
        if (issuingBank.getAbbreviatedName() == null || issuingBank.getAbbreviatedName().isEmpty()) {
            throw new IllegalArgumentException("Bank name is required");
        }
    }
}
