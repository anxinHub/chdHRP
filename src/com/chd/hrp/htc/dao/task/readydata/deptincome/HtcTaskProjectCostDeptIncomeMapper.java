package com.chd.hrp.htc.dao.task.readydata.deptincome;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.readydata.deptincome.HtcTaskProjectCostDeptIncome;

public interface HtcTaskProjectCostDeptIncomeMapper  extends SqlMapper{
	
	public int disposeHtcTaskProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	 public List<HtcTaskProjectCostDeptIncome> queryHtcTaskProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	    
	 public List<HtcTaskProjectCostDeptIncome> queryHtcTaskProjectCostDeptIncome(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
}
