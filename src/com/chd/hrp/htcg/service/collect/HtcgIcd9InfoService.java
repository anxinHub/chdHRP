package com.chd.hrp.htcg.service.collect;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.collect.HtcgIcd9Info;


public interface HtcgIcd9InfoService {
	
	public String addIcdHtcg9Info(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgIcd9Info(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcgIcd9Info queryHtcgIcd9InfoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgIcd9Info(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgIcd9Info(List<Map<String,Object>> list)throws DataAccessException;
	
	public String updateHtcgIcd9Info(Map<String,Object> entityMap)throws DataAccessException;

	
}
