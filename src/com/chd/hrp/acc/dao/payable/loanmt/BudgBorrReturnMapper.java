package com.chd.hrp.acc.dao.payable.loanmt;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.payable.BudgBorrReturn;

public interface BudgBorrReturnMapper extends SqlMapper{
	public int addBudgBorrReturn(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchBudgBorrReturn(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateBudgBorrReturn(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBatchBudgBorrReturn(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int deleteBudgBorrReturn(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchBudgBorrReturn(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<BudgBorrReturn> queryBudgBorrReturn(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<BudgBorrReturn> queryBudgBorrReturn(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public BudgBorrReturn queryBudgBorrReturnByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public BudgBorrReturn queryBudgBorrReturnByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAmount(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateConfirmPay(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public Map<String,Object> queryBorrReturnByPrintTemlate(Map<String,Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryBudgBorrReturnPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}	
