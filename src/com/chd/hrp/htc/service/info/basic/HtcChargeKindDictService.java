package com.chd.hrp.htc.service.info.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.info.basic.HtcChargeKindDict;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcChargeKindDictService {

	public String addHtcChargeKindDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcChargeKindDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcChargeKindDict queryHtcChargeKindDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteHtcChargeKindDict(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteBatchHtcChargeKindDict(List<Map<String,Object>> entityList) throws DataAccessException;
	 
	public String updateHtcChargeKindDict(Map<String,Object> entityMap)throws DataAccessException;
}
