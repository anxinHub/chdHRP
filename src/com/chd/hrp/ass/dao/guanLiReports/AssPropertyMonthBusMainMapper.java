package com.chd.hrp.ass.dao.guanLiReports;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssPropertyMonthBusMainMapper extends SqlMapper{
	public List<Map<String, Object>> queryAssPropertyBusMonthMain(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<Map<String, Object>> queryAssPropertyBusMonthMain(Map<String, Object> entityMap)throws DataAccessException;
}
