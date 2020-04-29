package com.chd.hrp.htc.dao.income.business.dept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.income.business.dept.HtcIncomeChargeCostDept;

public interface HtcIncomeChargeCostDeptMapper extends SqlMapper{
	
	public Map<String, Object> collectHtcIncomeChargeCostDept(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcIncomeChargeCostDept> queryHtcIncomeChargeCostDept(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcIncomeChargeCostDept> queryHtcIncomeChargeCostDept(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
