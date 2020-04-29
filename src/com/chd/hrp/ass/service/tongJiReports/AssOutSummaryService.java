package com.chd.hrp.ass.service.tongJiReports;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 资产出库统计
 * @author fhqfm
 *
 */
public interface AssOutSummaryService {      

	/**
	 * 资产出库统计
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssOutSummary(Map<String, Object> entityMap)throws DataAccessException;

	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssOutMainSummaryPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryOutSituation(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryOutSituationPrint(Map<String, Object> entityMap) throws DataAccessException;

}
