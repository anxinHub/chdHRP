package com.chd.hrp.acc.service.payable.otherpay;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.payable.BudgChargeApply;

public interface BudgChargeApplyForAccService {
	
	//查询
	public String queryBudgChargeApplyPayForAcc(Map<String,Object> entityMap) throws DataAccessException;
	
}
