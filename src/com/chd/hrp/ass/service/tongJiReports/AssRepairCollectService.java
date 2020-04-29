package com.chd.hrp.ass.service.tongJiReports;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssRepairCollectService {

	String queryAssRepairCollect(Map<String, Object> mapVo);
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssRepairCollectPrint(Map<String, Object> entityMap) throws DataAccessException;

}
