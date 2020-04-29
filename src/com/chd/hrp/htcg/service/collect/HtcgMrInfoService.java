package com.chd.hrp.htcg.service.collect;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.collect.HtcgMrInfo;


public interface HtcgMrInfoService {

	public String addHtcgMrInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgMrInfo(List<Map<String,Object>> list)throws DataAccessException;
	
	public String queryHtcgMrInfo(Map<String,Object> entityMap) throws DataAccessException;

	public HtcgMrInfo queryHtcgMrInfoByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteHtcgMrInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgMrInfo(List<Map<String,Object>> list)throws DataAccessException;
	
	public String updateHtcgMrInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	public String importHtcgMrInfo(Map<String, Object> entityMap) throws DataAccessException;
}
