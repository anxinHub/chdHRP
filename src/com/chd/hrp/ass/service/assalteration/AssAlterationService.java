package com.chd.hrp.ass.service.assalteration;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssAlterationService {

	String queryAssAlteration(Map<String, Object> mapVo);
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssAlterationMainPrint(Map<String, Object> entityMap) throws DataAccessException;

}
