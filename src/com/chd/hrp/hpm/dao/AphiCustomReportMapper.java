package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptTargetData;

public interface AphiCustomReportMapper extends SqlMapper {
	
	/**
	 * 
	 */
	public List<AphiDeptTargetData> queryAphiCustomReportHead(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<Map<String,Object>>  queryAphiCustomReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 打印
	 */
	public List<Map<String,Object>>  queryAphiCustomReportPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String,Object>>  queryAphiCustomReport(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
