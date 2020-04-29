package com.chd.hrp.htc.dao.relative.plan.set;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HtcRelativePlanDeptMapper extends SqlMapper{
	
	public int addHtcRelativePlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcRelativePlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcRelativePlanDept(List<Map<String,Object>> list)throws DataAccessException;
	
	public int deleteBatchHtcRelativePlanDept(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<Map<String,Object>> queryHtcRelativePlanDeptByPlanSetOk(Map<String,Object> entityMap)throws DataAccessException;
	 
	public List<Map<String,Object>> queryHtcRelativePlanDeptByPlanSetNO(Map<String,Object> entityMap)throws DataAccessException;
	 
	

}
