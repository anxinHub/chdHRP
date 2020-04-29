package com.chd.hrp.mat.service.account.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 科室出库查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportDeptOutSearchService {
	
	/**
	 * @Description 
	 * 查询报表<BR>   
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatAccountReportDeptOutSearch(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryMatAccountReportDeptOutSearchPrint(Map<String,Object> entityMap) throws DataAccessException;

	public String queryMatAccountReportDeptOutSearchByCollect(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryMatAccountReportDeptOutSearchByCollectPrint(Map<String, Object> entityMap) throws DataAccessException;
}
