package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcMaterialDict;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcMaterialDictService {

	public String addHtcMaterialDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcMaterialDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcMaterialDict queryHtcMaterialDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcMaterialDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcMaterialDict(List<Map<String, Object>> entityList) throws DataAccessException;

	public String updateHtcMaterialDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String synchroHtcMaterialDict(Map<String,Object> entityMap)throws DataAccessException;
}
