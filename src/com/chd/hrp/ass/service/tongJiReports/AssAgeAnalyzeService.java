package com.chd.hrp.ass.service.tongJiReports;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssAgeAnalyzeService  {


	String queryAssCardUseYearOrLifeYear(Map<String, Object> mapVo);
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssAgeAnalyePrint(Map<String, Object> entityMap) throws DataAccessException;


}
