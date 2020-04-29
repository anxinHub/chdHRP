package com.chd.hrp.htcg.service.setout;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface HtcgSchemeMrSampleDataService {
	
	public String initHtcgSchemeMrSampleData(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgSchemeMrSampleData(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcgSchemeMrSampleData(List<Map<String,Object>> list )throws DataAccessException;
	
	
}
