package com.chd.hrp.acc.service.autovouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface AccAutoHisLogMainService {
	
	public String queryAccHisViewLog(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccHisViewLogPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryAutoHisViewSetting(Map<String, Object> entityMap) throws DataAccessException;
	
	public String deleteBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String update(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateDate(Map<String,Object> entityMap)throws DataAccessException;
	
	public <T> T  queryByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String,Object> runJob(String etlPath,String jobPath,String viewCode)throws Exception;
	
}
