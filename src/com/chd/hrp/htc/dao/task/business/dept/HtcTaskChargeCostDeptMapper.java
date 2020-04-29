package com.chd.hrp.htc.dao.task.business.dept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.business.dept.HtcTaskChargeCostDept;

public interface HtcTaskChargeCostDeptMapper extends SqlMapper{

    public Map<String, Object> collectHtcTaskChargeCostDept(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcTaskChargeCostDept> queryHtcTaskChargeCostDept(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcTaskChargeCostDept> queryHtcTaskChargeCostDept(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
