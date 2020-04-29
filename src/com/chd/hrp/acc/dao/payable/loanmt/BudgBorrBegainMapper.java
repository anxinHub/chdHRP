package com.chd.hrp.acc.dao.payable.loanmt;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.payable.BudgBorrBegain;

public interface BudgBorrBegainMapper extends SqlMapper{
	public int addBudgBorrBegain(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchBudgBorrBegain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateBudgBorrBegain(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchBudgBorrBegain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int deleteBudgBorrBegain(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchBudgBorrBegain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<BudgBorrBegain> queryBudgBorrBegain(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<BudgBorrBegain> queryBudgBorrBegain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public BudgBorrBegain queryBudgBorrBegainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public BudgBorrBegain queryBudgBorrBegainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAmount(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryBudgBorrBegainPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}	
