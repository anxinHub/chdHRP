package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcMaterialTypeDict;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcMaterialTypeDictService {

	public String addHtcMaterialTypeDict(Map<String,Object> entityMap)throws DataAccessException;

	public String queryHtcMaterialTypeDict(Map<String,Object> entityMap) throws DataAccessException;

	public HtcMaterialTypeDict queryHtcMaterialTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteHtcMaterialTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcMaterialTypeDict(List<Map<String, Object>> entityList)throws DataAccessException;
	
	public String updateHtcMaterialTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String synchroHtcMaterialTypeDict(Map<String,Object> entityMap)throws DataAccessException;
}
