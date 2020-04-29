package com.chd.hrp.ass.dao.assquery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssDefinedQueryMapper extends SqlMapper {

	List<Map<String, Object>> queryAssDefinedQuery(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryAssDefinedQuery(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	List<Map<String, Object>> queryFields(Map<String, Object> entityMap) throws DataAccessException;
	
	int addAssDefinedQuery(Map<String,Object> entityMap)throws DataAccessException;
	
	int updateAssDefinedQuery(Map<String,Object> entityMap)throws DataAccessException;
	
	int deleteBatchAssDefinedQuery(List<Map> params)throws DataAccessException;
	
	List<Map<String,Object>> queryAssReportItem(Map<String,Object> entityMap)throws DataAccessException;
	
	int addBatchAssReportItem(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	int deleteAssReportItemByRheadId(Map<String,Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssDefinedQueryRun(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssDefinedQueryRun(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
}
