package com.chd.hrp.htc.dao.relative.readydata.deptincome;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.income.readydata.deptincome.HtcIncomeProjectCostDeptIncome;
import com.chd.hrp.htc.entity.relative.readydata.deptincome.HtcRelativeProjectCostDeptIncome;

public interface HtcRelativeProjectCostDeptIncomeMapper extends SqlMapper{
	
	 public int disposeHtcRelativeProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	 public List<HtcRelativeProjectCostDeptIncome> queryHtcRelativeProjectCostDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	    
	 public List<HtcRelativeProjectCostDeptIncome> queryHtcRelativeProjectCostDeptIncome(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
