package com.chd.hrp.htc.dao.income.readydata.deptincome;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.income.readydata.deptincome.HtcIncomeProjectCostDeptIncome;

public interface HtcIncomeProjectCostDeptIncomeMapper extends SqlMapper{
	
	 public int disposeHtcIncomeProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	 public List<HtcIncomeProjectCostDeptIncome> queryHtcIncomeProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	    
	 public List<HtcIncomeProjectCostDeptIncome> queryHtcIncomeProjectCostDeptIncome(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
