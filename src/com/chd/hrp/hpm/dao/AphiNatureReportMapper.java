package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface AphiNatureReportMapper extends SqlMapper{
	
	/**
	 * 查询
	 */
	public List<Map<String,Object>> queryHpmNatureReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询
	 */
	public List<Map<String,Object>> queryHpmNatureReport(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
