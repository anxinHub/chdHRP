package com.chd.hrp.htcg.service.setout;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface HtcgSchemeMrGroupDataService {
	
	
	
	public String initHtcgSchemeMrGroupData(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgSchemeMrGroupData(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcgSchemeMrGroupData(List<Map<String,Object>> list)throws DataAccessException;
	
}
