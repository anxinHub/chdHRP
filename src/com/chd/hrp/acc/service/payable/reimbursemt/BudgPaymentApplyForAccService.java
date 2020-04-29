package com.chd.hrp.acc.service.payable.reimbursemt;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BudgPaymentApplyForAccService {

	
	public String queryBudgPaymentApplyForAcc(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryBudgPaymentApplyForAccPrint(Map<String,Object> entityMap) throws DataAccessException;

}
