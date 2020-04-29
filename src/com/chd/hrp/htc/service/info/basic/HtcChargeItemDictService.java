package com.chd.hrp.htc.service.info.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.info.basic.HtcChargeItemDict;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcChargeItemDictService {
	
	public String queryHtcChargeItemDicts(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addHtcChargeItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcChargeItemDicts(List<Map<String,Object>> entityMap)throws DataAccessException;

	public String queryHtcChargeItemDict(Map<String,Object> entityMap) throws DataAccessException;

	public HtcChargeItemDict queryHtcChargeItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcChargeItemDict(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteBatchHtcChargeItemDict(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public String updateHtcChargeItemDict(Map<String,Object> entityMap)throws DataAccessException;
}
