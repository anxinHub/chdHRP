package com.chd.hrp.med.service.termend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MedFinalChargeService  extends SqlService{

    public String updateMedFinalCharge(Map<String, Object> entityMap) throws DataAccessException ;
    public String updateMedFinalInverse(Map<String, Object> entityMap) throws DataAccessException ;
    public String queryExistsMedFinalUnconfirm(Map<String, Object> entityMap) throws DataAccessException ;
    public String queryYearMonthByStoreSet(Map<String, Object> entityMap) throws DataAccessException ;
}
