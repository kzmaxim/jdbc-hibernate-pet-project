package service;

import model.MerchantCategoryCode;

import java.util.List;
import java.util.Optional;

public interface MerchantCategoryCodeService {
    void createMCC(MerchantCategoryCode mcc);
    Optional<MerchantCategoryCode> getMCCById(Long id);
    List<MerchantCategoryCode> getAllMCCs();
    void updateMCC(MerchantCategoryCode mcc);
    void deleteMCC(Long id);
    void clearAllMCCs();
    Optional<MerchantCategoryCode> getMCCByCode(String mccCode);
}
