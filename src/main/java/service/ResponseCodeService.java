package main.java.service;

import main.java.model.ResponseCode;

import java.util.List;
import java.util.Optional;

public interface ResponseCodeService {
    void createResponseCode(ResponseCode responseCode);
    Optional<ResponseCode> getResponseCodeById(Long id);
    List<ResponseCode> getAllResponseCodes();
    void updateResponseCode(ResponseCode responseCode);
    void deleteResponseCode(Long id);
    void clearAllResponseCodes();
    Optional<ResponseCode> getResponseCodeByErrorCode(String errorCode);
    List<ResponseCode> getResponseCodesByLevel(String errorLevel);
}
