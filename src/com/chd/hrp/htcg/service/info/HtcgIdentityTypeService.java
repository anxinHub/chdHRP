package com.chd.hrp.htcg.service.info;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.info.HtcgIdentityType;

public interface HtcgIdentityTypeService {
	
	public String addHtcgIdentityType(Map<String, Object> entityMap) throws DataAccessException;
	
	public String addBatchHtcgIdentityType(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryHtcgIdentityType(Map<String, Object> entityMap) throws DataAccessException;
	
	public HtcgIdentityType queryHtcgIdentityTypeByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	public String deleteHtcgIdentityType(Map<String, Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcgIdentityType(List<Map<String,Object>> entityList) throws DataAccessException;
	
	public String updateHtcgIdentityType(Map<String, Object> entityMap) throws DataAccessException;
	
	public String importHtcgIdentityType(Map<String, Object> entityMap) throws DataAccessException;
	
}
