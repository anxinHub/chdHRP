package com.chd.hrp.acc.dao.payable.reimbursemt;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface BudgPaymentApplyForAccMapper extends SqlMapper {

	public List<Map<String, Object>> queryBudgPaymentApplyForAcc(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryBudgPaymentApplyForAcc(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryBudgPaymentApplyForAccPrint(Map<String, Object> entityMap) throws DataAccessException;
	
}
