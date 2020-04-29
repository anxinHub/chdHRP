package com.chd.hrp.acc.service.payable.loanmt;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BudgBorrPayForAccService {

	public String queryBudgBorrPayForAcc(Map<String, Object> entityMap) throws DataAccessException;

}
