package com.chd.hrp.mat.service.dura.termend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MatDuraFinalChargeService extends SqlService{

    public String updateMatDuraFinalCharge(Map<String, Object> entityMap) throws DataAccessException ;
    public String updateMatDuraFinalInverse(Map<String, Object> entityMap) throws DataAccessException ;
    public String queryExistsMatDuraFinalUnconfirm(Map<String, Object> entityMap) throws DataAccessException ;
    
}
