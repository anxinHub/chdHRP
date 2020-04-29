package com.chd.hrp.htc.dao.task.business.hos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.business.hos.HtcTaskChargeCostHos;

public interface HtcTaskChargeCostHosMapper extends SqlMapper{

	 public Map<String, Object> collectHtcTaskChargeCostHos(Map<String,Object> entityMap)throws DataAccessException;
		
	 public List<HtcTaskChargeCostHos> queryHtcTaskChargeCostHos(Map<String,Object> entityMap)throws DataAccessException;
	    
	 public List<HtcTaskChargeCostHos> queryHtcTaskChargeCostHos(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
