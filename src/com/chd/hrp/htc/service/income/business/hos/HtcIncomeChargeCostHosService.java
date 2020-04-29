package com.chd.hrp.htc.service.income.business.hos;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcIncomeChargeCostHosService {
	
    public String addHtcIncomeChargeCostHos(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryHtcIncomeChargeCostHos(Map<String, Object> entityMap) throws DataAccessException;

}
