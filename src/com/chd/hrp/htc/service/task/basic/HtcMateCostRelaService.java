package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcMateCostRela;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcMateCostRelaService {

	public String addHtcMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcMateCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcMateCostRela queryHtcMateCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcMateCostRela(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String updateHtcMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
}
