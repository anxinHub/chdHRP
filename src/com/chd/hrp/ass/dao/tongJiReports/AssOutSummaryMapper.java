package com.chd.hrp.ass.dao.tongJiReports;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 资产出库统计
 * @author fhqfm
 *
 */
public interface AssOutSummaryMapper extends SqlMapper {        

	List<Map<String, Object>> queryAssOutSummary(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryAssOutSummary(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	List<Map<String, Object>> queryAssOutMainSummaryPrint(
			Map<String, Object> entityMap);

	
	
	List<Map<String, Object>> queryOutSituation(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryOutSituation(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	List<Map<String, Object>> queryOutSituationPrint(
			Map<String, Object> entityMap);
	
}
