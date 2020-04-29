package com.chd.hrp.ass.service.summary;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssSummaryService {

	String queryAssSummary(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssSummaryMainPrint(Map<String, Object> entityMap) throws DataAccessException;

}
