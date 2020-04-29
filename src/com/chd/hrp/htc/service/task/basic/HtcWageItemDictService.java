package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcWageItemDict;
/**
 * 2015-3-17 author:alfred
 */


public interface HtcWageItemDictService {

	public String addHtcWageItemDict(Map<String,Object> entityMap)throws DataAccessException;

	public String queryHtcWageItemDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcWageItemDict queryHtcWageItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteBatchHtcWageItemDict(List<Map<String, Object>> entityList)throws DataAccessException;

	public String updateHtcWageItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String impHtcWageItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}
