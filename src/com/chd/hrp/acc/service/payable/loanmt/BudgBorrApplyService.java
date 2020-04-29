package com.chd.hrp.acc.service.payable.loanmt;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.payable.BudgBorrApply;

public interface BudgBorrApplyService {
	public String addBudgBorrApply(Map<String,Object> entityMap)throws DataAccessException;
	
	 
	public String addBatchBudgBorrApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateBudgBorrApply(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchBudgBorrApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteBudgBorrApply(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchBudgBorrApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateSubmitAndWithdraw(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String updateConfirmPay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryBudgBorrApply(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String queryBudgBorrApplyDet(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public BudgBorrApply queryBudgBorrApplyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public BudgBorrApply queryBudgBorrApplyByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryBorrApplyPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	//借款申请单  模板打印  PageOffice
	public Map<String, Object> queryBorrApplyPrintTemlateNew(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryBudgBorrApplyPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String unConfirmPay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
