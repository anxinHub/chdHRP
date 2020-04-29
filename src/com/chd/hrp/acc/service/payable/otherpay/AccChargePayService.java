package com.chd.hrp.acc.service.payable.otherpay;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccChargePayService {

	public String updateBudgUnit(Map<String, Object> paraMap) throws DataAccessException;
	
}
