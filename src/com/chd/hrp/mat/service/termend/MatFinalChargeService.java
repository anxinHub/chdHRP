package com.chd.hrp.mat.service.termend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MatFinalChargeService  extends SqlService{

    public String updateMatFinalCharge(Map<String, Object> entityMap) throws DataAccessException ;
    public String updateMatFinalInverse(Map<String, Object> entityMap) throws DataAccessException ;
    public String queryExistsMatFinalUnconfirm(Map<String, Object> entityMap) throws DataAccessException ;
    public String queryYearMonthByStoreSet(Map<String, Object> entityMap) throws DataAccessException ;
}
