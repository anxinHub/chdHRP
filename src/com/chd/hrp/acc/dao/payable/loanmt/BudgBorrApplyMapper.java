package com.chd.hrp.acc.dao.payable.loanmt;
 
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.payable.BudgBorrApply;

public interface BudgBorrApplyMapper extends SqlMapper{
	public int addBudgBorrApply(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchBudgBorrApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateBudgBorrApply(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchBudgBorrApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateSubmitAndWithdraw(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int deleteBudgBorrApply(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchBudgBorrApply(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<BudgBorrApply> queryBudgBorrApply(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<BudgBorrApply> queryBudgBorrApply(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public BudgBorrApply queryBudgBorrApplyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public BudgBorrApply queryBudgBorrApplyByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAmount(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateConfirmPay(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public int updateUnConfirmPay(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public Map<String,Object> queryBorrApplyByPrintTemlate(Map<String,Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryBorrApplyDetByPrintTemlateDetail(Map<String,Object> entityMap);
	
	public List<Map<String,Object>> queryBudgBorrApplyPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}	
