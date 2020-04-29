package com.chd.hrp.htc.dao.relative.business.dept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.relative.business.dept.HtcRelativeChargeCostDept;

public interface HtcRelativeChargeCostDeptMapper extends SqlMapper{
	
	public Map<String, Object> collectHtcRelativeChargeCostDept(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcRelativeChargeCostDept> queryHtcRelativeChargeCostDept(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcRelativeChargeCostDept> queryHtcRelativeChargeCostDept(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
