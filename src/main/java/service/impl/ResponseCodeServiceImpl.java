package service.impl;


import dao.Dao;
import dao.hibernate.ResponseCodeHibernateDaoImpl;
import dao.jdbc.ResponseCodeJDBCDaoImpl;
import model.ResponseCode;
import service.ResponseCodeService;

import java.util.List;
import java.util.Optional;

public class ResponseCodeServiceImpl implements ResponseCodeService {
    private final Dao<ResponseCode> responseCodeDao;

    public ResponseCodeServiceImpl() {
        this.responseCodeDao = new ResponseCodeHibernateDaoImpl();
    }

    @Override
    public void createResponseCode(ResponseCode responseCode) {
        validateResponseCode(responseCode);
        responseCodeDao.add(responseCode);
    }

    @Override
    public Optional<ResponseCode> getResponseCodeById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid response code ID");
        }
        return responseCodeDao.getById(id);
    }

    @Override
    public List<ResponseCode> getAllResponseCodes() {
        return responseCodeDao.getAll();
    }

    @Override
    public void updateResponseCode(ResponseCode responseCode) {
        validateResponseCode(responseCode);
        responseCodeDao.update(responseCode);
    }

    @Override
    public void deleteResponseCode(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid response code ID");
        }
        responseCodeDao.deleteById(id);
    }

    @Override
    public void clearAllResponseCodes() {
        responseCodeDao.clearTable();
    }

    @Override
    public Optional<ResponseCode> getResponseCodeByErrorCode(String errorCode) {
        if (errorCode == null || errorCode.isEmpty()) {
            throw new IllegalArgumentException("Error code cannot be empty");
        }
        return responseCodeDao.getAll().stream()
                .filter(rc -> errorCode.equals(rc.getErrorCode()))
                .findFirst();
    }

    @Override
    public List<ResponseCode> getResponseCodesByLevel(String errorLevel) {
        if (errorLevel == null || errorLevel.isEmpty()) {
            throw new IllegalArgumentException("Error level cannot be empty");
        }
        return responseCodeDao.getAll().stream()
                .filter(rc -> errorLevel.equalsIgnoreCase(rc.getErrorLevel()))
                .toList();
    }

    private void validateResponseCode(ResponseCode responseCode) {
        if (responseCode == null) {
            throw new IllegalArgumentException("Response code cannot be null");
        }
        if (responseCode.getErrorCode() == null || responseCode.getErrorCode().isEmpty()) {
            throw new IllegalArgumentException("Error code is required");
        }
        if (responseCode.getErrorDescription() == null || responseCode.getErrorDescription().isEmpty()) {
            throw new IllegalArgumentException("Error description is required");
        }
        if (responseCode.getErrorLevel() == null || responseCode.getErrorLevel().isEmpty()) {
            throw new IllegalArgumentException("Error level is required");
        }
    }
}
