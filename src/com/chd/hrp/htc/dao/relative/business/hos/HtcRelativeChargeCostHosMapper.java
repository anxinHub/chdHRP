package com.chd.hrp.htc.dao.relative.business.hos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.relative.business.hos.HtcRelativeChargeCostHos;
 
public interface HtcRelativeChargeCostHosMapper extends SqlMapper{
	
	public Map<String, Object> addHtcRelativeChargeCostHos(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcRelativeChargeCostHos> queryHtcRelativeChargeCostHos(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcRelativeChargeCostHos> queryHtcRelativeChargeCostHos(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
