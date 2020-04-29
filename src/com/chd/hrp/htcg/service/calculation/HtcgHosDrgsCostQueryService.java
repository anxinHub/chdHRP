package com.chd.hrp.htcg.service.calculation;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcgHosDrgsCostQueryService {
	
	public String queryHtcgHosDrgsCostQuery(Map<String,Object> entityMap) throws DataAccessException; 

}
