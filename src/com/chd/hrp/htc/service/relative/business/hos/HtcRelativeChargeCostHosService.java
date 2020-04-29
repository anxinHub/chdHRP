package com.chd.hrp.htc.service.relative.business.hos;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcRelativeChargeCostHosService {
	
    public String addHtcRelativeChargeCostHos(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryHtcRelativeChargeCostHos(Map<String, Object> entityMap) throws DataAccessException;

}
