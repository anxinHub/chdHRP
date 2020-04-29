package com.chd.hrp.htc.dao.income.plan.set;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HtcIncomePlanDeptMapper extends SqlMapper{
	
	public int addHtcIncomePlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcIncomePlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcIncomePlanDept(List<Map<String,Object>> list)throws DataAccessException;
	
	public int deleteBatchHtcIncomePlanDept(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<Map<String,Object>> queryHtcIncomePlanDeptByPlanSetOk(Map<String,Object> entityMap)throws DataAccessException;
	 
	public List<Map<String,Object>> queryHtcIncomePlanDeptByPlanSetNO(Map<String,Object> entityMap)throws DataAccessException;
	 
	

}
