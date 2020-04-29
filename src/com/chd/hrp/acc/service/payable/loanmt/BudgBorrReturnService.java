package com.chd.hrp.acc.service.payable.loanmt;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.payable.BudgBorrReturn;

public interface BudgBorrReturnService {
	public String addBudgBorrReturn(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addBatchBudgBorrReturn(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateBudgBorrReturn(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchBudgBorrReturn(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteBudgBorrReturn(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchBudgBorrReturn(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateConfirmPay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryBudgBorrReturn(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String queryBudgBorrReturnDet(Map<String,Object> entityMap) throws DataAccessException;
	
	public BudgBorrReturn queryBudgBorrReturnByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public BudgBorrReturn queryBudgBorrReturnByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryBorrReturnPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryBudgBorrReturnPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
