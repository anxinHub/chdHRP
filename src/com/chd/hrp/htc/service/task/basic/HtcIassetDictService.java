package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcIassetDict;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcIassetDictService {

	public String addHtcIassetDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcIassetDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcIassetDict queryHtcIassetDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteHtcIassetDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcIassetDict(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public String updateHtcIassetDict(Map<String,Object> entityMap)throws DataAccessException;
}
