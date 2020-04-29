package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AphiHosPerfWageRatioReportMapper  extends SqlMapper{
	
	/*
	 * 查询
	 */
	public List<Map<String,Object>> queryHpmHosPerfWageRatioReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * 查询-打印
	 */
	public List<Map<String,Object>> queryHpmHosPerfWageRatioReportPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * 查询
	 */
	public List<Map<String,Object>> queryHpmHosPerfWageRatioReportByCollect(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * 生成
	 */
	public int addHpmHosPerfWageRatioReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * 修改
	 */
	public int updateHpmHosPerfWageRatioReport (Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * 删除
	 */
	public int deleteHpmHosPerfWageRatioReport (Map<String, Object> entityMap) throws DataAccessException;
	
	
	public int queryisaudit (Map<String, Object> entityMap) throws DataAccessException;
	
	public int shenhe (Map<String, Object> entityMap) throws DataAccessException;
}
