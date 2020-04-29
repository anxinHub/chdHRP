package com.chd.hrp.ass.service.assquery;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 资产综合查询
 * 
 * @author fhqfm
 *
 */
public interface AssDefinedQueryService {

	/**
	 * 自定义查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAssDefinedQuery(Map<String, Object> entityMap) throws DataAccessException;

	List<String> queryFields(Map<String, Object> entityMap) throws DataAccessException;

	Map<String, Object> queryByCode(Map<String, Object> entityMap) throws DataAccessException;

	String addAssDefinedQuery(Map<String, Object> entityMap) throws DataAccessException;
	
	List<Map<String,Object>> queryAssReportItem(Map<String, Object> entityMap) throws DataAccessException;

	String queryAssDefinedQueryRun(Map<String, Object> entityMap) throws DataAccessException;

	String deleteAssDefinedQuery(Map<String, Object> entityMap) throws DataAccessException;

	String updateAssDefinedQuery(Map<String, Object> entityMap) throws DataAccessException;

}
