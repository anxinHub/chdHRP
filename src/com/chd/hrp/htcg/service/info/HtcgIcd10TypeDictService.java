package com.chd.hrp.htcg.service.info;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.info.HtcgIcd10TypeDict;

public interface HtcgIcd10TypeDictService {
	
	public String addHtcgIcd10TypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgIcd10TypeDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public String queryHtcgIcd10TypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public HtcgIcd10TypeDict queryHtcgIcd10TypeDictDicByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgIcd10TypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgIcd10TypeDict(List<Map<String,Object>> entityList)throws DataAccessException;

	public String updateHtcgIcd10TypeDict(Map<String, Object> entityMap)throws DataAccessException;
 
	public String impIcd10TypeDictReadFiles(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
