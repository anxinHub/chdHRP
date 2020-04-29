package com.chd.hrp.acc.service.payable.loanmt;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.payable.BudgBorrBegain;

public interface BudgBorrBegainService {
	public String addBudgBorrBegain(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addBatchBudgBorrBegain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateBudgBorrBegain(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchBudgBorrBegain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteBudgBorrBegain(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchBudgBorrBegain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String queryBudgBorrBegain(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String queryBudgBorrBegainDet(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public BudgBorrBegain queryBudgBorrBegainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public BudgBorrBegain queryBudgBorrBegainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public String accountBudgBorrBegain(Map<String,Object> entityMap)throws DataAccessException;
	
	public boolean isAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryBudgBorrBegainPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
