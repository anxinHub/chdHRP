package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcIassetCostRela;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcIassetCostRelaService {

	public String addHtcIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;

	public String queryHtcIassetCostRela(Map<String,Object> entityMap) throws DataAccessException;

	public HtcIassetCostRela queryHtcIassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteHtcIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcIassetCostRela(List<Map<String, Object>> entityList) throws DataAccessException;

	public String updateHtcIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
}
