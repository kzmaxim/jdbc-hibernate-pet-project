package service.impl;

import dao.Dao;
import dao.hibernate.AcquiringBankHibernateDaoImpl;
import dao.jdbc.AcquiringBankJDBCDaoImpl;
import model.AcquiringBank;
import service.AcquiringBankService;

import java.util.List;
import java.util.Optional;

public class AcquiringBankServiceImpl implements AcquiringBankService {
    private final Dao<AcquiringBank> acquiringBankDao;

    public AcquiringBankServiceImpl() {
        this.acquiringBankDao = new AcquiringBankHibernateDaoImpl();
    }
    public AcquiringBankServiceImpl(Dao<AcquiringBank> dao) {
        this.acquiringBankDao = dao;
    }

    @Override
    public void createAcquiringBank(AcquiringBank acquiringBank) {
        validateAcquiringBank(acquiringBank);
        acquiringBankDao.add(acquiringBank);
    }

    @Override
    public Optional<AcquiringBank> getAcquiringBankById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid acquiring bank ID");
        }
        return acquiringBankDao.getById(id);
    }

    @Override
    public List<AcquiringBank> getAllAcquiringBanks() {
        return acquiringBankDao.getAll();
    }

    @Override
    public void updateAcquiringBank(AcquiringBank acquiringBank) {
        validateAcquiringBank(acquiringBank);
        acquiringBankDao.update(acquiringBank);
    }

    @Override
    public void deleteAcquiringBank(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid acquiring bank ID");
        }
        acquiringBankDao.deleteById(id);
    }

    @Override
    public void clearAllAcquiringBanks() {
        acquiringBankDao.clearTable();
    }

    private void validateAcquiringBank(AcquiringBank acquiringBank) {
        if (acquiringBank == null) {
            throw new IllegalArgumentException("Acquiring bank cannot be null");
        }
        if (acquiringBank.getBic() == null || acquiringBank.getBic().isEmpty()) {
            throw new IllegalArgumentException("BIC is required");
        }
        if (acquiringBank.getAbbreviatedName() == null || acquiringBank.getAbbreviatedName().isEmpty()) {
            throw new IllegalArgumentException("Abbreviated name is required");
        }
    }
}
