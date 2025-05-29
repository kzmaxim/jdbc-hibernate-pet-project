package service.impl;

import dao.Dao;
import dao.hibernate.MerchantCategoryCodeHibernateDaoImpl;
import dao.jdbc.MerchantCategoryCodeJDBCDaoImpl;
import model.MerchantCategoryCode;
import service.MerchantCategoryCodeService;

import java.util.List;
import java.util.Optional;

public class MerchantCategoryCodeServiceImpl implements MerchantCategoryCodeService {
    private final Dao<MerchantCategoryCode> mccDao;

    public MerchantCategoryCodeServiceImpl() {
        this.mccDao = new MerchantCategoryCodeHibernateDaoImpl();
    }
    public MerchantCategoryCodeServiceImpl(Dao<MerchantCategoryCode> mccDao) {
        this.mccDao = mccDao;
    }

    @Override
    public void createMCC(MerchantCategoryCode mcc) {
        validateMCC(mcc);
        mccDao.add(mcc);
    }

    @Override
    public Optional<MerchantCategoryCode> getMCCById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid MCC ID");
        }
        return mccDao.getById(id);
    }

    @Override
    public List<MerchantCategoryCode> getAllMCCs() {
        return mccDao.getAll();
    }

    @Override
    public void updateMCC(MerchantCategoryCode mcc) {
        validateMCC(mcc);
        mccDao.update(mcc);
    }

    @Override
    public void deleteMCC(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid MCC ID");
        }
        mccDao.deleteById(id);
    }

    @Override
    public void clearAllMCCs() {
        mccDao.clearTable();
    }

    @Override
    public Optional<MerchantCategoryCode> getMCCByCode(String mccCode) {
        if (mccCode == null || mccCode.isEmpty()) {
            throw new IllegalArgumentException("MCC code cannot be empty");
        }
        return mccDao.getAll().stream()
                .filter(m -> mccCode.equals(m.getMcc()))
                .findFirst();
    }

    private void validateMCC(MerchantCategoryCode mcc) {
        if (mcc == null) {
            throw new IllegalArgumentException("MCC cannot be null");
        }
        if (mcc.getMcc() == null || mcc.getMcc().isEmpty()) {
            throw new IllegalArgumentException("MCC code is required");
        }
        if (mcc.getMccName() == null || mcc.getMccName().isEmpty()) {
            throw new IllegalArgumentException("MCC name is required");
        }
    }
}
