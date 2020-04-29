package com.chd.hrp.hpm.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiHosPerfWageRatioReportService {
	
	/**
	 * @Description 
	 * <BR>报表 查询
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryHpmHosPerfWageRatioReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR>报表 打印
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryHpmHosPerfWageRatioReportPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR>生成
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String initHpmHosPerfWageRatioReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR>报表 修改
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateHpmHosPerfWageRatioReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR>报表 删除
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteHpmHosPerfWageRatioReport(Map<String, Object> entityMap) throws DataAccessException;

	String shenhe(Map<String, Object> entityMap) throws DataAccessException;
}
