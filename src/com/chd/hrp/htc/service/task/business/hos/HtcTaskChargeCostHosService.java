package com.chd.hrp.htc.service.task.business.hos;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcTaskChargeCostHosService {

	public String queryHtcTaskChargeCostHos(Map<String, Object> entityMap) throws DataAccessException;
	
	public String collectHtcTaskChargeCostHos(Map<String, Object> entityMap) throws DataAccessException;
}
