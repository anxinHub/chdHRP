package com.chd.hrp.htc.service.task.cost.deptincome;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.cost.deptincome.HtcTaskDeptIncome;

public interface HtcTaskDeptIncomeService {
	/**
	 * 
	 */
	public String addHtcTaskDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	 
	/**
	 * 
	 */
	public String queryHtcTaskDeptIncome(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public HtcTaskDeptIncome queryHtcTaskDeptIncomeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	public String deleteHtcTaskDeptIncome (Map<String,Object> entityMap)throws DataAccessException; 
	
	/**
	 * 
	 */
	public String deleteBatchHtcTaskDeptIncome(List<Map<String,Object>> list)throws DataAccessException;
	
	/**
	 * 
	 */
	public String updateHtcTaskDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	public String impHtcTaskDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
}
