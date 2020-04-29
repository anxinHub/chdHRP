package com.chd.hrp.htcg.service.collect;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcgIntegratedQueryService {
	
	public String queryHtcgIntegratedQuery(Map<String, Object> mapVo)throws DataAccessException;
}
