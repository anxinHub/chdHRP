package com.chd.hrp.htc.dao.income.readydata.deptcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.income.readydata.deptcost.HtcIncomeProjectCostDeptCost;
import com.chd.hrp.htc.entity.income.readydata.deptincome.HtcIncomeProjectCostDeptIncome;

public interface HtcIncomeProjectCostDeptCostMapper extends SqlMapper{
	
	 public int disposeHtcIncomeProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	
	 public List<HtcIncomeProjectCostDeptCost> queryHtcIncomeProjectCostDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	    
	 public List<HtcIncomeProjectCostDeptCost> queryHtcIncomeProjectCostDeptCost(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
