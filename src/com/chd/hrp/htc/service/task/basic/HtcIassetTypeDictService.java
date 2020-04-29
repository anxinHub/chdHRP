package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcIassetTypeDict;
/** 
* 2015-3-18 
* author:alfred
*/ 
public interface HtcIassetTypeDictService {

	public String addHtcIassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcIassetTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcIassetTypeDict queryHtcIassetTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteHtcIassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteBatchHtcIassetTypeDict(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public String updateHtcIassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
}
