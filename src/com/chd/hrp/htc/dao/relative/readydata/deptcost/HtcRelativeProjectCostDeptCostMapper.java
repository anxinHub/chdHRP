package com.chd.hrp.htc.dao.relative.readydata.deptcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.relative.readydata.deptcost.HtcRelativeProjectCostDeptCost;

public interface HtcRelativeProjectCostDeptCostMapper extends SqlMapper{
	
	 public int disposeHtcRelativeProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	
	 public List<HtcRelativeProjectCostDeptCost> queryHtcRelativeProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	    
	 public List<HtcRelativeProjectCostDeptCost> queryHtcRelativeProjectCostDeptCost(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
