package com.chd.hrp.htcg.dao.calculation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgHosPdrgsCost;

public interface HtcgHosDrgsCostQueryMapper extends SqlMapper{ 

	
	public List<HtcgHosPdrgsCost> queryHtcgHosDrgsCostQuery(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgHosPdrgsCost> queryHtcgHosDrgsCostQuery(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
