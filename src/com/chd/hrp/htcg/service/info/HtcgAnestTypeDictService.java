package com.chd.hrp.htcg.service.info;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.info.HtcgAnestTypeDict;

public interface HtcgAnestTypeDictService {
	
	public String addHtcgAnestTypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgAnestTypeDict(List<Map<String, Object>> list)throws DataAccessException;
	
	public String queryHtcgAnestTypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public HtcgAnestTypeDict queryHtcgAnestTypeDictByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgAnestTypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgAnestTypeDict(List<Map<String, Object>> list)throws DataAccessException;

	public String updateHtcgAnestTypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public String importHtcgAnestTypeDict(Map<String, Object> entityMap) throws DataAccessException;
}
