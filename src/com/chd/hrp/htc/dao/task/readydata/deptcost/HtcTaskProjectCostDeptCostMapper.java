package com.chd.hrp.htc.dao.task.readydata.deptcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.readydata.deptcost.HtcTaskProjectCostDeptCost;

public interface HtcTaskProjectCostDeptCostMapper extends SqlMapper{
	
	 public int disposeHtcTaskProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
		
	 public List<HtcTaskProjectCostDeptCost> queryHtcTaskProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	    
	 public List<HtcTaskProjectCostDeptCost> queryHtcTaskProjectCostDeptCost(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
